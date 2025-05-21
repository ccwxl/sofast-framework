package cc.sofast.framework.starter.redis.codec;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

/**
 * @author wxl
 */
public class SofastGenericJackson2JsonRedisSerializer extends GenericJackson2JsonRedisSerializer {

    public SofastGenericJackson2JsonRedisSerializer(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    public Object deserialize(byte[] source) throws SerializationException {
        try {
            Class<?> clazz = RedisDeserializeHelper.get();
            if (clazz != null) {
                return deserialize(source, clazz);
            }
        } catch (Exception e) {
            return super.deserialize(source);
        }
        return super.deserialize(source);
    }

    @Override
    public <T> T deserialize(byte[] source, Class<T> type) throws SerializationException {
        return super.deserialize(source, type);
    }
}
