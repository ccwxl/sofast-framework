package cc.sofast.biz.component.file;

import cc.sofast.biz.component.common.ComponentExtensionPoint;
import org.dromara.x.file.storage.core.FileInfo;

/**
 * @author wxl
 */
public interface FileExtensionPoint extends ComponentExtensionPoint {
    /**
     * 上传文件之前
     *
     * @param params 上传参数
     */
    default void uploadBefore(FileUploadParams params) {
    }

    /**
     * 上传文件之后
     *
     * @param info 文件信息
     */
    default void uploadAfter(FileInfo info) {
    }

    /**
     * 下载文件之前
     *
     * @param info 文件信息
     */
    default void downloadBefore(FileInfo info) {
    }
}
