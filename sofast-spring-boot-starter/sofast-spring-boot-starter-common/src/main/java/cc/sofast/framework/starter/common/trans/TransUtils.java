package cc.sofast.framework.starter.common.trans;

import cc.sofast.framework.starter.common.trans.core.ListSerializerTranslator;
import cc.sofast.framework.starter.common.trans.core.SerializerTranslator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;

/**
 * @author wxl
 */
public class TransUtils {


    public static void trans(Object obj) {
        Class<?> clazz = obj.getClass();
        TypeFactory factory = TypeFactory.defaultInstance();
        JavaType javaType = factory.constructType(clazz);
        System.out.println(javaType);
        //CollectionType
        SerializerTranslator<Object> se = getSerializerTranslator(javaType);
        se.serialize(obj);
        //ArrayType
        //MapType
        //SimpleType

    }

    private static SerializerTranslator getSerializerTranslator(JavaType javaType) {
        if (javaType instanceof CollectionType) {

            return new ListSerializerTranslator();
        }
        return null;
    }

}
