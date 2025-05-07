package cc.sofast.framework.starter.common.trans.translator;

import cc.sofast.framework.starter.common.trans.Trans;
import cc.sofast.framework.starter.common.trans.Translator;
import cc.sofast.framework.starter.common.utils.SpringUtils;
import org.springframework.core.ResolvableType;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * @author wxl
 */
public class TranslatorField<T> {
    private Field field;
    private Field originField;
    private Translator<T, Annotation> transformer;
    private Annotation transformAnnotation;

    public TranslatorField(Field field) {
        this.field = field;
        Trans mergedAnnotation = AnnotatedElementUtils.getMergedAnnotation(field, Trans.class);
        Assert.notNull(mergedAnnotation, "字段" + field.getName() + "上必须标注@Trans注解或其衍生注解");
        String originFieldName = mergedAnnotation.value();
        this.originField = ReflectionUtils.findField(field.getDeclaringClass(), originFieldName);
        Class<? extends Translator> translatorClass = mergedAnnotation.translator();
        this.transformer = SpringUtils.getBean(translatorClass);
        // 获取自定义注解类型（Transformer上有两个泛型，第一个是转换前的值类型，第二个是是自定义注解类型）
        ResolvableType resolvableType = ResolvableType.forClass(Translator.class, translatorClass);
        Class<? extends Annotation> customTransformAnnotationType = (Class<? extends Annotation>) resolvableType.getGeneric(1).resolve();
        Assert.notNull(customTransformAnnotationType, "实现Transform接口时必须指定泛型：" + transformer.getClass().getSimpleName());
        this.transformAnnotation = field.getAnnotation(customTransformAnnotationType);
    }

    public void translate(Object value) {
        T originalValue = (T) ReflectionUtils.getField(originField, value);
        String transform = transformer.transform(originalValue, transformAnnotation);
        ReflectionUtils.setField(field, value, transform);
    }
}
