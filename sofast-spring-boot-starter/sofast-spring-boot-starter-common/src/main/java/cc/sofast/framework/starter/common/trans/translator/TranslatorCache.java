package cc.sofast.framework.starter.common.trans.translator;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wxl
 */
public final class TranslatorCache {
    /**
     * 转换类的缓存，提高性能
     */
    private static final Map<Class<?>, TranslatorClass> TRANSLATOR_CLASS = new ConcurrentHashMap<>(512);


    public TranslatorClass getTranslatorClass(Object obj) {
        if (obj == null) {
            return null;
        }
        return getTranslatorClassFromClass(obj.getClass());
    }

    public static TranslatorClass getTranslatorClassFromClass(Class<?> clazz) {
        return TRANSLATOR_CLASS.computeIfAbsent(clazz, TranslatorClass::new);
    }
}
