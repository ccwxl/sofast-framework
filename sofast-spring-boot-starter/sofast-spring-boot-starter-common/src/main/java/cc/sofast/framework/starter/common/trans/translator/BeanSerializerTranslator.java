package cc.sofast.framework.starter.common.trans.translator;

/**
 * @author wxl
 */
public class BeanSerializerTranslator extends SerializerTranslator<Object> {

    //TODO class ---> 标注@Trans注解的 filed cache
    //TODO class ---> ref 字段cache
    //TODO 有可能循环嵌套,避免深度遍历导致出现问题

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
