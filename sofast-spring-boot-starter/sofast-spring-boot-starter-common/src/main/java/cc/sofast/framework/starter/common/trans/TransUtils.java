package cc.sofast.framework.starter.common.trans;

import cc.sofast.framework.starter.common.trans.translator.SerializerTranslator;
import cc.sofast.framework.starter.common.trans.translator.SerializerTranslatorFactory;
import cc.sofast.framework.starter.common.trans.translator.TransContext;

/**
 * @author wxl
 */
public class TransUtils {

    @SuppressWarnings("unchecked")
    public static void trans(Object obj) {
        Class<?> clazz = obj.getClass();
        @SuppressWarnings("rawtypes") SerializerTranslator se = SerializerTranslatorFactory.getSerializerTranslator(clazz);
        if (se == null) {
            return;
        }
        TransContext context = new TransContext();
        try {
            se.serialize(obj, context);
        } finally {
            context.exit();
        }
    }
}
