package cc.sofast.framework.starter.common.exception;

import cc.sofast.framework.starter.common.enums.BaseEnum;
import lombok.NonNull;

/**
 * @author wxl
 */
public class ServiceException extends RuntimeException {

    public ServiceException(ErrorCode errorCode) {
        super(errorCode.getMessage());
    }

    /**
     * 从枚举类生成异常（错误信息支持模板参数填充）
     *
     * @param customEnum     枚举类对象
     * @param templateParams label 中如果有占位符的话，向里面填充的模板参数
     */
    public ServiceException(@NonNull BaseEnum<?> customEnum, Object... templateParams) {
        super(customEnum.formatLabel(templateParams));
    }

    public ServiceException(String msg) {
        super(msg);
    }
}
