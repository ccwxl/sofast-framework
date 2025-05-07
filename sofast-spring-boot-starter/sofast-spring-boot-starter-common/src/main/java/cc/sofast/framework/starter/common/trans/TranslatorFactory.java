package cc.sofast.framework.starter.common.trans;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wxl
 */
public class TranslatorFactory {

    /**
     * 翻译器缓存
     */
    private final static Map<Class<?>, Translator> TRANSLATOR_MAP = new ConcurrentHashMap<>();

    /**
     * 获取翻译器
     */
    public static Translator getTranslator(Class<? extends Translator> clazz) {
        Translator translator = TRANSLATOR_MAP.get(clazz);
        return translator;
    }

    /**
     * 注册翻译器
     */
    public static void registerTranslator(Translator translator) {
        TRANSLATOR_MAP.put(translator.getClass(), translator);
    }
}
