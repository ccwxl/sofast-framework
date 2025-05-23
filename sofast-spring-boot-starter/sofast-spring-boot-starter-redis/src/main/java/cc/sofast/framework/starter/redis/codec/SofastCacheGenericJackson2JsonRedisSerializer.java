package cc.sofast.framework.starter.redis.codec;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JacksonObjectReader;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.lang.reflect.Type;

/**
 * @author wxl
 */
public class SofastCacheGenericJackson2JsonRedisSerializer extends GenericJackson2JsonRedisSerializer {

    private final JacksonObjectReader reader;

    private final ObjectMapper mapper;

    public SofastCacheGenericJackson2JsonRedisSerializer(ObjectMapper objectMapper) {
        super(objectMapper);
        this.reader = JacksonObjectReader.create();
        this.mapper = objectMapper;
    }

    @Override
    public Object deserialize(byte[] source) throws SerializationException {
        try {
            Type clazz = RedisDeserializeHelper.getGenericType();
            if (clazz != null) {
                return deserialize(source, clazz);
            }
        } catch (Exception e) {
            return super.deserialize(source);
        }
        return super.deserialize(source);
    }

    public <T> T deserialize(byte[] source, Type type) throws SerializationException {
        Assert.notNull(type, "Deserialization type must not be null;"
                + " Please provide Object.class to make use of Jackson2 default typing.");

        if (isEmpty(source)) {
            return null;
        }

        try {
            JavaType javaType = TypeFactory.defaultInstance().constructType(type);
            return (T) reader.read(mapper, source, javaType);
        } catch (Exception ex) {
            throw new SerializationException("Could not read JSON:%s ".formatted(ex.getMessage()), ex);
        }
    }

    static boolean isEmpty(@Nullable byte[] data) {
        return (data == null || data.length == 0);
    }
}
