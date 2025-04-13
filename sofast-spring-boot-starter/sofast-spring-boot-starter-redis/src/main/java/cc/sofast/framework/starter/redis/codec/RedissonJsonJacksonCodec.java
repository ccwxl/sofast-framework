package cc.sofast.framework.starter.redis.codec;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.redisson.client.codec.StringCodec;
import org.redisson.client.protocol.Decoder;
import org.redisson.client.protocol.Encoder;
import org.redisson.codec.JsonJacksonCodec;

/**
 * @author wxl
 */
public class RedissonJsonJacksonCodec extends JsonJacksonCodec {

    public RedissonJsonJacksonCodec(ObjectMapper objectMapper) {
        super(objectMapper);
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
