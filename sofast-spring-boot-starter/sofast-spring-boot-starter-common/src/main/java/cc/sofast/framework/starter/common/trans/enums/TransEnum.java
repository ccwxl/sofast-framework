package cc.sofast.framework.starter.common.trans.enums;

import cc.sofast.framework.starter.common.enums.BaseEnum;
import cc.sofast.framework.starter.common.trans.Trans;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @author wxl
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target({ElementType.FIELD})
@Trans(translator = EnumTranslator.class)
public @interface TransEnum {

    /**
     * 来源字段
     */
    @AliasFor(annotation = Trans.class)
    String ref() default "";

    /**
     * 枚举class，必须实现了BaseEnum接口
     */
    @SuppressWarnings("rawtypes")
    Class<? extends BaseEnum<?>> typ();
}
