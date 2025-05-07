package cc.sofast.framework.starter.common.trans.dict;

import cc.sofast.framework.starter.common.trans.Trans;
import cc.sofast.framework.starter.common.trans.enums.EnumTranslator;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @author wxl
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target({ElementType.FIELD})
@Trans(translator = EnumTranslator.class)
public @interface TransDict {

    /**
     * 来源字段
     */
    @AliasFor("refFiled")
    String value() default "";

    /**
     * 来源字段
     */
    @AliasFor("value")
    String refFiled() default "";

    /**
     * 字典分组
     */
    String group();
}
