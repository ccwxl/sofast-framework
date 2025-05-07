package cc.sofast.framework.starter.common.trans.translator;

/**
 * @author wxl
 */
public class BeanSerializerTranslator extends SerializerTranslator<Object> {

    private final TranslatorCache translatorCache = new TranslatorCache();

    @Override
    public void serialize(Object value, TransContext transContext) {
        //判断嵌套次数是
        int objHashCode = System.identityHashCode(value);
        TranslatorClass translatorClass = translatorCache.getTranslatorClass(value);
        if (translatorClass == null) {
            // 获取不到转换类，表示object为null或者空集合
            return;
        }
        transContext.increment(objHashCode);
        //处理字段
        translatorClass.translate(value, transContext);
    }
}
