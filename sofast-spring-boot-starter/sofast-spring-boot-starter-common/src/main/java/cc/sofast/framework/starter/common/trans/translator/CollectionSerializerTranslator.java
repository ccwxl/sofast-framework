package cc.sofast.framework.starter.common.trans.translator;

import java.util.Collection;

/**
 * list,set,array
 *
 * @author wxl
 */
public class CollectionSerializerTranslator extends SerializerTranslator<Collection<?>> {

    @Override
    public void serialize(Collection<?> value, TransContext transContext) {
        //batch trans

    }
}
