package cc.sofast.framework.starter.common.trans.dict;

import cc.sofast.framework.starter.common.trans.Trans;
import cc.sofast.framework.starter.common.trans.enums.EnumTranslator;

import java.lang.annotation.*;

/**
 * @author wxl
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target({ElementType.FIELD})
@Trans(transformer = EnumTranslator.class)
public @interface TransDict {
    /**
     * 来源字段
     */
    String value();

    /**
     * 字典分组
     */
    String group();
}
