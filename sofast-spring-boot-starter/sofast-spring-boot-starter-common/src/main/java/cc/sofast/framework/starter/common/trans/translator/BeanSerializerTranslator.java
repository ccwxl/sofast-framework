package cc.sofast.framework.starter.common.trans.translator;

/**
 * @author wxl
 */
public class BeanSerializerTranslator extends SerializerTranslator<Object> {

    //TODO class ---> 标注@Trans注解的 filed cache
    //TODO class ---> ref 字段cache
    //TODO 有可能循环嵌套,避免深度遍历导致出现问题

    @Override
    public void serialize(Object value) {

    }
}
