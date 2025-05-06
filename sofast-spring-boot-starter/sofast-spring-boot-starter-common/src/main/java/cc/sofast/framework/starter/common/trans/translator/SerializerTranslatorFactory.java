package cc.sofast.framework.starter.common.trans.translator;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.*;

/**
 * @author wxl
 */
public class SerializerTranslatorFactory {
    private static final TypeFactory FACTORY = TypeFactory.defaultInstance();

    private SerializerTranslatorFactory() {

    }

    @SuppressWarnings("rawtypes")
    public static SerializerTranslator getSerializerTranslator(Class<?> clazz) {
        JavaType javaType = FACTORY.constructType(clazz);
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
