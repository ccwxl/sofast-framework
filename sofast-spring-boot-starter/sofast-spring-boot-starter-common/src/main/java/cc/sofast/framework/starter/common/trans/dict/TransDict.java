package cc.sofast.framework.starter.common.trans.dict;

import cc.sofast.framework.starter.common.trans.Trans;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @author wxl
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target({ElementType.FIELD})
@Trans(translator = DictTranslator.class)
public @interface TransDict {

    /**
     * 来源字段
     */
    @AliasFor(annotation = Trans.class)
    String ref() default "";

    /**
     * 字典分组
     */
    String group();
}
