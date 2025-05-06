package cc.sofast.framework.starter.common.trans.translator;

/**
 * @author wxl
 */
public abstract class SerializerTranslator<T> {

    public abstract void serialize(T value, TransContext transContext);

}
