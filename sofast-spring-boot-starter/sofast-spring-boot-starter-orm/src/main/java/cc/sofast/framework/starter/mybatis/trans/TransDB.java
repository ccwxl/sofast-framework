package cc.sofast.framework.starter.mybatis.trans;

import cc.sofast.framework.starter.common.trans.Trans;
import cc.sofast.framework.starter.common.trans.enums.EnumTranslator;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @author wxl
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target({ElementType.FIELD})
@Trans(translator = EnumTranslator.class)
public @interface TransDB {
    /**
     * 来源字段
     */
    @AliasFor(annotation = Trans.class)
    String ref() default "";

    /**
     * 必须实现自BaseMapper接口
     */
    @SuppressWarnings("all")
    Class<? extends BaseMapper<?>> mapper();

    /**
     * 使用哪个字段进行填充(翻译),默认为标注此注解的字段名
     */
    String to();
}
