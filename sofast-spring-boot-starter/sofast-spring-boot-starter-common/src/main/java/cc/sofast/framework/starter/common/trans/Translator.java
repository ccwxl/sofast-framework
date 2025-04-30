package cc.sofast.framework.starter.common.trans;


import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;

/**
 * 转换器接口
 *
 * @author wxl
 * @since 2022-9-27
 */
public interface Translator<T, A extends Annotation> {

    /**
     * 翻译
     *
     * @param refVal     转换之前的原始值(使用哪个字段进行翻译)
     * @param annotation 自定义注解
     * @return 翻译后的值
     */
    default String transform(T refVal, A annotation) {
        if (refVal == null) {
            return null;
        }
        return refVal.toString();
    }

    /**
     * 批量翻译
     *
     * @param refVal     转换之前的原始值(使用哪个字段进行翻译)
     * @param annotation 自定义注解
     * @return 翻译后的值 (key:refVal,value:转换前的值)
     */
    default Map<String, Object> transform(List<T> refVal, A annotation) {

        return Map.of();
    }
}
