package cc.sofast.biz.component.file;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author wxl
 */
@Data
@Schema(description = "上传参数")
public class FileUploadParams {

    @Schema(description = "存储平台")
    private String platform;

    @Schema(description = "私有/共有")
    private FileAccessLevel acl;

    @Schema(description = "业务类型")
    private String bizType;

    @Schema(description = "业务标识")
    private String bizKey;
}
