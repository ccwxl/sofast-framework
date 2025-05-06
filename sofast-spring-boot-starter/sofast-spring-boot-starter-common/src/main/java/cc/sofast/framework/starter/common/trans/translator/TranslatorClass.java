package cc.sofast.framework.starter.common.trans.translator;

import cc.sofast.framework.starter.common.trans.TransUtils;
import lombok.Data;

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
     * 需要嵌套转换的字段及其对应的转换类,只处理第一层嵌套,多层嵌套不进行处理
     */
    private Map<Field, Class<?>> nestTransformFields = new HashMap<>();

    public void translate(Object value) {
        for (TranslatorField<?> translatorField : translatorFields) {
            translatorField.translate(value);
        }
        for (Map.Entry<Field, Class<?>> entry : nestTransformFields.entrySet()) {
            Field key = entry.getKey();
            Class<?> clazz = entry.getValue();

            TransUtils.trans(key);
        }
    }
}
