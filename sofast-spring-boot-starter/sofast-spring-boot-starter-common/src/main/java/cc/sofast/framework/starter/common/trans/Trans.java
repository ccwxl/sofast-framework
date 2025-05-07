package cc.sofast.framework.starter.common.trans;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @author apple
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.TYPE})
public @interface Trans {
    /**
     * 来源字段
     */
    @AliasFor("ref")
    String value() default "";

    /**
     * 来源字段
     */
    @AliasFor("value")
    String ref() default "";

    /**
     * 指定转换器
     */
    Class<? extends Translator> translator() default KvTranslator.class;

}
