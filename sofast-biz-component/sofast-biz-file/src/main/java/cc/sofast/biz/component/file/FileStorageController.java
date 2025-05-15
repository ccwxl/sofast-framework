package cc.sofast.biz.component.file;

import cc.sofast.framework.starter.common.dto.PageParam;
import cc.sofast.framework.starter.common.dto.PageResult;
import cc.sofast.framework.starter.common.dto.Result;
import cc.sofast.framework.starter.mybatis.utils.PageUtil;
import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.dromara.x.file.storage.core.FileInfo;
import org.dromara.x.file.storage.core.FileStorageService;
import org.dromara.x.file.storage.core.constant.Constant;
import org.dromara.x.file.storage.spring.SpringFileStorageProperties;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
@RequestMapping(value = "${sofast.file.api.base-path}/file")
@ConditionalOnProperty(prefix = "sofast.file.api", name = "enabled", havingValue = "true", matchIfMissing = true)
public class FileStorageController {
    private final FileStorageService fileStorageService;


    private final FileProperties fileProperties;

    private final FileDetailService fileDetailService;

    private final SpringFileStorageProperties fileStorageProperties;

    /**
     * 上传文件
     *
     * @param file MultipartFile
     * @return Result<FileInfo>
     */
    @Operation(summary = "上传文件")
    @PostMapping("/upload")
    public Result<FileInfo> upload(@Schema(hidden = true) HttpServletRequest request,
                                   @RequestParam(required = false) String platform,
                                   @RequestParam(required = false, defaultValue = "public") FileAccessLevel acl,
                                   MultipartFile file) {
        if (StringUtils.isBlank(platform)) {
            platform = fileStorageProperties.getDefaultPlatform();
        }

        //验证文件类型
        String name = file.getName();
        String requestContentType = request.getContentType();
        if (fileProperties.denied(name, MediaType.parseMediaType(requestContentType))) {
            return Result.fail("不允许上传此文件类型");
        }

        //上传文件
        FileInfo fileInfo = fileStorageService
                .of(file)
                .setPlatform(platform)
                .thumbnail(SofastFileUtils.isImage(file))
                .setAcl(acl.getCode())
                .upload();
        return Result.ok(fileInfo);
    }

    /**
     * 文件列表
     * TODO 只能看到公共的和自己上传的
     *
     * @param pageParam 分页参数
     * @return PageResult<FileDetail>
     */
    @Operation(summary = "文件列表")
    @GetMapping("/page")
    public PageResult<FileDetail> page(@ParameterObject PageParam pageParam) {
        Page<FileDetail> page = fileDetailService.page(PageUtil.buildPage(pageParam));
        return PageUtil.toPageResult(page);
    }

    /**
     * download
     * 下载自己上传的和公共的
     */

}
