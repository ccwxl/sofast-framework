package cc.sofast.framework.starter.common.trans.translator;

import lombok.Data;
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
    private Map<Field, Class<?>> nestTransformFields = new HashMap<>();

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
        for (Map.Entry<Field, Class<?>> entry : nestTransformFields.entrySet()) {
            Field key = entry.getKey();
            Class<?> clazz = entry.getValue();
            @SuppressWarnings("rawtypes")
            SerializerTranslator se = SerializerTranslatorFactory.getSerializerTranslator(clazz);
            if (se == null) {
                continue;
            }
            Object fieldValue = ReflectionUtils.getField(key, value);
            se.serialize(fieldValue, transContext);
        }
    }
}
