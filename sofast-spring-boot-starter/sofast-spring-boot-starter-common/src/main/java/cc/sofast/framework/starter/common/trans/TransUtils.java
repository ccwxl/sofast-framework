package cc.sofast.framework.starter.common.trans;

import cc.sofast.framework.starter.common.trans.translator.BeanSerializerTranslator;
import cc.sofast.framework.starter.common.trans.translator.IndexListSerializerTranslator;
import cc.sofast.framework.starter.common.trans.translator.MapSerializerTranslator;
import cc.sofast.framework.starter.common.trans.translator.SerializerTranslator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.*;

/**
 * @author wxl
 */
public class TransUtils {

    private static final TypeFactory FACTORY = TypeFactory.defaultInstance();

    //TODO cache class ---> SerializerTranslator

    @SuppressWarnings("unchecked")
    public static void trans(Object obj) {
        Class<?> clazz = obj.getClass();
        JavaType javaType = FACTORY.constructType(clazz);
        @SuppressWarnings("rawtypes") SerializerTranslator se = getSerializerTranslator(javaType);
        se.serialize(obj);
    }

    @SuppressWarnings("rawtypes")
    private static SerializerTranslator getSerializerTranslator(JavaType javaType) {
        if (javaType instanceof SimpleType) {
            return new BeanSerializerTranslator();
        }
        if (javaType instanceof CollectionLikeType) {
            //java.util.RandomAccess
            return new IndexListSerializerTranslator();
        }
        if (javaType instanceof MapLikeType) {

            return new MapSerializerTranslator();
        }
        if (javaType instanceof ArrayType) {
            //java.util.RandomAccess
            return new IndexListSerializerTranslator();
        }
        return null;
    }

}
