package cc.sofast.framework.starter.common.trans;

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
    String value() default "";

    /**
     * 指定转换器
     */
    Class<? extends Transformer> transformer() default KvTransformer.class;

}
