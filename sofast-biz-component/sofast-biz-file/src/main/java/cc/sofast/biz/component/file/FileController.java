package cc.sofast.biz.component.file;

import cc.sofast.framework.starter.common.dto.PageParam;
import cc.sofast.framework.starter.common.dto.PageResult;
import cc.sofast.framework.starter.common.dto.Result;
import cc.sofast.framework.starter.common.enums.BaseEnum;
import cc.sofast.framework.starter.mybatis.utils.PageUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

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
@RequestMapping(value = "${sofast.file.api.base-path:'infra'}/file")
@ConditionalOnProperty(prefix = "sofast.file.api", name = "enabled", havingValue = "true", matchIfMissing = true)
public class FileController {
    private final FileStorageService fileStorageService;

    private final FileProperties fileProperties;

    private final SpringFileStorageProperties fileStorageProperties;

    private final FileService fileService;

    /**
     * 上传文件
     *
     * @param file MultipartFile
     * @return Result<FileInfo>
     */
    @Operation(summary = "上传文件")
    @PostMapping("/upload")
    public Result<FileInfo> fileUpload(@ParameterObject FileUploadParams params,
                                       MultipartFile file) {
        //hook before
        FileInfo fileInfo = FileStoreUtils.uploadFile(params, file);
        //hook after
        return Result.ok(fileInfo);
    }

    /**
     * 文件列表
     *
     * @param pageParam 分页参数
     * @return PageResult<FileDetail>
     */
    @Operation(summary = "文件列表")
    @GetMapping("/page")
    public PageResult<FileDO> page(@ParameterObject PageParam pageParam, @ParameterObject FileUploadParams queryParams) {
        Page<FileDO> page = fileService.selectPage(PageUtil.buildPage(pageParam), queryParams);
        return PageUtil.toPageResult(page);
    }

    /**
     * download 文件
     */
    @Operation(summary = "下载文件")
    @GetMapping("/download/{name}")
    public void download(@Schema(description = "文件名", requiredMode = Schema.RequiredMode.REQUIRED) @PathVariable String name,
                         @Schema(hidden = true) HttpServletResponse response) throws Exception {
        FileInfo fileInfo = FileStoreUtils.getFileInfo(name);
        Object fileAcl = fileInfo.getFileAcl();
        Optional<FileAccessLevel> fileAccessLevel = BaseEnum.of(FileAccessLevel.class, fileAcl);
        if (fileAccessLevel.isPresent() && fileAccessLevel.get() == FileAccessLevel.PRIVATE) {
            throw new FileException(FileErrorCode.FILE_PRIVATE_NOT_READER);
        }
        response.setContentType(fileInfo.getContentType());
        response.setHeader("Content-Disposition",
                "attachment;filename=" + URLEncoder.encode(fileInfo.getOriginalFilename(), StandardCharsets.UTF_8.name()));
        fileStorageService.download(fileInfo).outputStream(response.getOutputStream());
    }

    /**
     * download 图片类带缓存.
     */


}
