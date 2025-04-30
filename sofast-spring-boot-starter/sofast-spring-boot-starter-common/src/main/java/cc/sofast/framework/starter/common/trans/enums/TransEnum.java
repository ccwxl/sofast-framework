package cc.sofast.framework.starter.common.trans.enums;

import cc.sofast.framework.starter.common.enums.BaseEnum;
import cc.sofast.framework.starter.common.trans.Trans;

import java.lang.annotation.*;

/**
 * @author wxl
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target({ElementType.FIELD})
@Trans(transformer = EnumTranslator.class)
public @interface TransEnum {
    /**
     * 来源字段
     */
    String value() default "";

    /**
     * 枚举class，必须实现了BaseEnum接口
     */
    @SuppressWarnings("rawtypes")
    Class<? extends BaseEnum<?>> typ();
}
