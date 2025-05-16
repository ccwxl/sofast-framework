package cc.sofast.biz.component.file;

import lombok.Data;

/**
 * @author wxl
 */
@Data
public class FileUploadParams {

    private String platform;

    private FileAccessLevel acl;

    private String bizType;

    private String bizKey;
}
