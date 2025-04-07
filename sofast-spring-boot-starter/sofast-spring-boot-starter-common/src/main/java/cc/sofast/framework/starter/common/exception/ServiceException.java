package cc.sofast.framework.starter.common.exception;

/**
 * @author wxl
 */
public class ServiceException extends RuntimeException {

    public ServiceException(ErrorCode errorCode) {
        super(errorCode.getMessage());
    }
}
