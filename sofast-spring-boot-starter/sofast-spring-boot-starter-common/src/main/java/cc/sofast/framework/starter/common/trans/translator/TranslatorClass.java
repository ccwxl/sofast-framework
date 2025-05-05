package cc.sofast.framework.starter.common.trans.translator;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wxl
 */
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
}
