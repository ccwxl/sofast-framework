package cc.sofast.framework.starter.common.trans.translator;

import cc.sofast.framework.starter.common.trans.Trans;
import cc.sofast.framework.starter.common.trans.Translator;
import cc.sofast.framework.starter.common.trans.TranslatorFactory;
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
    private Translator<T, Annotation> translator;
    private Annotation translatorAnnotation;

    public TranslatorField(Field field) {
        ReflectionUtils.makeAccessible(field);
        this.field = field;
        Trans mergedAnnotation = AnnotatedElementUtils.getMergedAnnotation(field, Trans.class);
        Assert.notNull(mergedAnnotation, "字段" + field.getName() + "上必须标注@Trans注解或其衍生注解");
        String originFieldName = mergedAnnotation.ref();
        this.originField = ReflectionUtils.findField(field.getDeclaringClass(), originFieldName);
        ReflectionUtils.makeAccessible(originField);
        Class<? extends Translator> translatorClass = mergedAnnotation.translator();
        this.translator = TranslatorFactory.getTranslator(translatorClass);
        ResolvableType resolvableType = ResolvableType.forClass(Translator.class, translatorClass);
        Class<? extends Annotation> customAnnotationType = (Class<? extends Annotation>) resolvableType.getGeneric(1).resolve();
        Assert.notNull(customAnnotationType, "实现 Translator 接口时必须指定泛型：" + translator.getClass().getSimpleName());
        this.translatorAnnotation = field.getAnnotation(customAnnotationType);
    }

    public void translate(Object value) {
        T originalValue = (T) ReflectionUtils.getField(originField, value);
        String transValue = translator.translator(originalValue, translatorAnnotation);
        ReflectionUtils.setField(field, value, transValue);
    }
}
