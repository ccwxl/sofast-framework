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
    private static final Map<Class<?>, TranslatorClass> TRANSFORM_CLASS_CACHE = new ConcurrentHashMap<>(512);


    public TranslatorClass getTranslatorClass(Object obj) {
        if (obj == null) {
            return null;
        }
        return getTransformClassFromClass(obj.getClass());
    }

    public static TranslatorClass getTransformClassFromClass(Class<?> clazz) {
        return TRANSFORM_CLASS_CACHE.computeIfAbsent(clazz, TranslatorClass::new);
    }
}
