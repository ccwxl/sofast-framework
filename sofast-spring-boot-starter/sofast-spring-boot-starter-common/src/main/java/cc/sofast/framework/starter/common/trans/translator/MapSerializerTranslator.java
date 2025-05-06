package cc.sofast.framework.starter.common.trans.translator;

import java.util.Collection;
import java.util.Map;

/**
 * @author wxl
 */
public class MapSerializerTranslator extends SerializerTranslator<Map<?, ?>> {
    @Override
    public void serialize(Map<?, ?> value, TransContext transContext) {
        //只对values做处理
        Collection<?> values = value.values();
    }
}
