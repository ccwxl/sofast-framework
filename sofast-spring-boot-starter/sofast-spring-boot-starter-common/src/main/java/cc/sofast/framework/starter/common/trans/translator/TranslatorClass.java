package cc.sofast.framework.starter.common.trans.translator;

import cc.sofast.framework.starter.common.trans.Trans;
import lombok.Data;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wxl
 */
@Data
public class TranslatorClass {

    private Class<?> clazz;
    /**
     * 需要转换的字段集合
     */
    private List<TranslatorField<?>> translatorFields = new ArrayList<>();

    /**
     * 需要嵌套转换的字段及其对应的转换类
     */
    private Map<Field, Class<?>> nestTranslatorFields = new HashMap<>();

    /**
     * 构造函数
     */
    public TranslatorClass(Class<?> aClass) {
        this.clazz = aClass;
        ReflectionUtils.doWithFields(aClass, field -> {
            if (field.getType() == String.class && AnnotatedElementUtils.isAnnotated(field, Trans.class)) {
                // 需要转换的字段，字段上的注解链上需要有@Trans，且字段类型必须为String
                translatorFields.add(new TranslatorField<>(field));
            } else if (field.getType() != String.class && field.isAnnotationPresent(Trans.class)) {
                // 需要嵌套转换的字段，类型不为String，且字段上直接标注了@Trans
                nestTranslatorFields.put(field, getClassFromField(field));
            }
        });
    }

    private Class<?> getClassFromField(Field field) {

        return field.getType();
    }

    @SuppressWarnings("unchecked")
    public void translate(Object value, TransContext transContext) {
        if (transContext.enter(System.identityHashCode(value))) {
            // 超过最大深度，停止递归
            transContext.exit();
            return;
        }
        for (TranslatorField<?> translatorField : translatorFields) {
            translatorField.translate(value);
        }
        for (Map.Entry<Field, Class<?>> entry : nestTranslatorFields.entrySet()) {
            Field key = entry.getKey();
            Class<?> clazz = entry.getValue();
            @SuppressWarnings("rawtypes")
            SerializerTranslator se = SerializerTranslatorFactory.getSerializerTranslator(clazz);
            if (se == null) {
                continue;
            }
            ReflectionUtils.makeAccessible(key);
            Object fieldValue = ReflectionUtils.getField(key, value);
            se.serialize(fieldValue, transContext);
        }
    }
}
