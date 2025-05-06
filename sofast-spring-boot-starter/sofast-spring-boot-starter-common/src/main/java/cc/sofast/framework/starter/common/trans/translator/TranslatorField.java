package cc.sofast.framework.starter.common.trans.translator;

import cc.sofast.framework.starter.common.trans.Translator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wxl
 */
public class TranslatorField<T> {
    private Field field;
    private Field originField;
    private Translator<T, Annotation> transformer;
    private Annotation transformAnnotation;
    /**
     * 转换结果缓存，线程级别
     */
    private ThreadLocal<Map<T, String>> transformResultCache = ThreadLocal.withInitial(ConcurrentHashMap::new);

    public void translate(Object value) {

    }
}
