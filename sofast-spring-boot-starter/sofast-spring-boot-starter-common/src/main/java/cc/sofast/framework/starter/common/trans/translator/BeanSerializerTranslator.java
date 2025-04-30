package cc.sofast.framework.starter.common.trans.translator;

/**
 * @author wxl
 */
public class BeanSerializerTranslator extends SerializerTranslator<Object> {

    //TODO class ---> 标注@Trans注解的 filed cache
    //TODO class ---> ref 字段cache
    //TODO 有可能循环嵌套,这种场景需要将次字段进行忽略

    @Override
    public void serialize(Object value) {

    }
}
