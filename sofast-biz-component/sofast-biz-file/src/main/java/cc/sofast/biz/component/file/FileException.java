package cc.sofast.biz.component.file;

import cc.sofast.framework.starter.common.exception.ErrorCode;
import cc.sofast.framework.starter.common.exception.ServiceException;

/**
 * @author wxl
 */
public class FileException extends ServiceException {

    public FileException(ErrorCode errorCode) {
        super(errorCode);
    }
}
