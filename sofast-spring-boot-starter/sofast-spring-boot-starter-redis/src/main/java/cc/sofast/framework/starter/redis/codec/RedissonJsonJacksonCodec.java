package cc.sofast.framework.starter.redis.codec;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import org.redisson.client.codec.StringCodec;
import org.redisson.client.handler.State;
import org.redisson.client.protocol.Decoder;
import org.redisson.client.protocol.Encoder;
import org.redisson.codec.TypedJsonJacksonCodec;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * @author wxl
 */
public class RedissonJsonJacksonCodec extends TypedJsonJacksonCodec {

    public RedissonJsonJacksonCodec(ObjectMapper objectMapper) {
        super(Object.class, objectMapper);
    }

    public RedissonJsonJacksonCodec(Class<?> valueClass) {
        super(valueClass, ObjectMapperWrapper.getObjectMapper());
    }

    @Override
    public Decoder<Object> getMapKeyDecoder() {

        return StringCodec.INSTANCE.getDecoder();
    }

    @Override
    public Encoder getMapKeyEncoder() {

        return StringCodec.INSTANCE.getEncoder();
    }
}
