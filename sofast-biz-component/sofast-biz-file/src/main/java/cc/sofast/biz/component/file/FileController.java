package cc.sofast.biz.component.file;

import cc.sofast.biz.component.common.ExtensionHandler;
import cc.sofast.framework.starter.common.dto.PageParam;
import cc.sofast.framework.starter.common.dto.PageResult;
import cc.sofast.framework.starter.common.dto.Result;
import cc.sofast.framework.starter.common.enums.BaseEnum;
import cc.sofast.framework.starter.common.utils.LambdaExceptionUtil;
import cc.sofast.framework.starter.mybatis.utils.PageUtil;
import com.baomidou.mybatisplus.core.plugins.IgnoreStrategy;
import com.baomidou.mybatisplus.core.plugins.InterceptorIgnoreHelper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.x.file.storage.core.FileInfo;
import org.dromara.x.file.storage.core.FileStorageService;
import org.dromara.x.file.storage.spring.SpringFileStorageProperties;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * 文件相关接口
 *
 * @author wxl
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "(biz-file) 文件接口", description = "(biz-file) 文件接口")
@RequestMapping(value = "${sofast.file.api.base-path:infra}/file")
@ApiSupport(order = 1)
@ConditionalOnProperty(prefix = "sofast.file.api", name = "enabled", havingValue = "true", matchIfMissing = true)
public class FileController {
    private final FileStorageService fileStorageService;
    private final FileService fileService;

    /**
     * 上传文件
     *
     * @param file MultipartFile
     * @return Result<FileInfo>
     */
    @Operation(summary = "上传文件")
    @PostMapping("/upload")
    @ApiOperationSupport(order = 1)
    public Result<FileInfo> fileUpload(@ParameterObject FileUploadParams params,
                                       MultipartFile file) {
        //hook before
        ExtensionHandler.exec(FileExtensionPoint.class, ext -> ext.uploadBefore(params));
        FileInfo fileInfo = FileStoreUtils.uploadFile(params, file);
        //hook after
        ExtensionHandler.exec(FileExtensionPoint.class, ext -> ext.uploadAfter(fileInfo));
        return Result.ok(fileInfo);
    }

    /**
     * 文件列表
     * 只能查看自己上传和公共的文件
     *
     * @param pageParam 分页参数
     * @return PageResult<FileDetail>
     */
    @Operation(summary = "文件列表")
    @GetMapping("/page")
    @ApiOperationSupport(order = 2)
    public PageResult<FileDO> page(@ParameterObject PageParam pageParam, @ParameterObject FileUploadParams queryParams) {
        Page<FileDO> page = fileService.selectPage(PageUtil.buildPage(pageParam), queryParams);
        return PageUtil.toPageResult(page);
    }

    /**
     * download 文件
     * 只能查看自己上传和公共的文件
     */
    @Operation(summary = "下载文件")
    @GetMapping("/download/{name}")
    @ApiOperationSupport(order = 3)
    public void download(@Schema(description = "文件名", requiredMode = Schema.RequiredMode.REQUIRED) @PathVariable String name,
                         @Schema(hidden = true) HttpServletResponse response) throws Exception {
        FileInfo fileInfo = FileStoreUtils.getFileInfo(name);
        validateAcl(fileInfo);
        ExtensionHandler.exec(FileExtensionPoint.class, ext -> ext.downloadBefore(fileInfo));
        response.setContentType(fileInfo.getContentType());
        response.setHeader("Content-Disposition",
                "attachment;filename=" + URLEncoder.encode(fileInfo.getOriginalFilename(), StandardCharsets.UTF_8.name()));
        fileStorageService.download(fileInfo).outputStream(response.getOutputStream());
    }

    /**
     * download 图片类带缓存.
     * 只能查看自己上传和公共的文件
     */
    @Operation(summary = "下载图片(带缓存)")
    @GetMapping("/images/{name}")
    @ApiOperationSupport(order = 4)
    public ResponseEntity<StreamingResponseBody> downloadImage(@Schema(description = "文件名", requiredMode = Schema.RequiredMode.REQUIRED)
                                                               @PathVariable String name) {
        FileInfo fileInfo = FileStoreUtils.getFileInfo(name);
        validateAcl(fileInfo);
        ExtensionHandler.exec(FileExtensionPoint.class, ext -> ext.downloadBefore(fileInfo));
        try {
            StreamingResponseBody responseBody = outputStream ->
                    fileStorageService.download(fileInfo).outputStream(outputStream);
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(fileInfo.getContentType()))
                    .contentLength(fileInfo.getSize())
                    .cacheControl(CacheControl.maxAge(30, TimeUnit.DAYS))
                    .body(responseBody);
        } catch (Exception e) {
            log.error("Download images failed name:[{}] originFile:[{}] reason:[{}]", name, fileInfo.getOriginalFilename(), e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 缩略图
     * 只能查看自己上传和公共的文件
     */
    @Operation(summary = "缩略图(带缓存)")
    @GetMapping("/thumbnail/{name}")
    @ApiOperationSupport(order = 5)
    public ResponseEntity<StreamingResponseBody> thumbnail(@Schema(description = "文件名", requiredMode = Schema.RequiredMode.REQUIRED)
                                                           @PathVariable String name) throws Exception {
        FileInfo fileInfo = FileStoreUtils.getFileInfo(name);
        validateAcl(fileInfo);
        ExtensionHandler.exec(FileExtensionPoint.class, ext -> ext.downloadBefore(fileInfo));
        try {
            StreamingResponseBody responseBody = outputStream ->
                    fileStorageService.downloadTh(fileInfo).outputStream(outputStream);
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(fileInfo.getContentType()))
                    .contentLength(fileInfo.getSize())
                    .cacheControl(CacheControl.maxAge(30, TimeUnit.DAYS))
                    .body(responseBody);
        } catch (Exception e) {
            log.error("Download thumbnail failed name:[{}] originFile:[{}] reason:[{}]", name, fileInfo.getOriginalFilename(), e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    void validateAcl(FileInfo fileInfo) {
        Object fileAcl = fileInfo.getFileAcl();
        Optional<FileAccessLevel> fileAccessLevel = BaseEnum.of(FileAccessLevel.class, fileAcl);
        if (fileAccessLevel.isPresent() && fileAccessLevel.get() == FileAccessLevel.PRIVATE) {
            throw new FileException(FileErrorCode.FILE_PRIVATE_NOT_READER);
        }
    }

}
