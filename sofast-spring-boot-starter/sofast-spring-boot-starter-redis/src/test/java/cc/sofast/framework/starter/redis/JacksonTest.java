package cc.sofast.framework.starter.redis;

import cc.sofast.framework.starter.redis.codec.ObjectMapperWrapper;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.codec.TypedJsonJacksonCodec;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wxl
 */
public class JacksonTest {

    @Test
    public void testFileName() throws JsonProcessingException {
        ObjectMapperWrapper wrapper = new ObjectMapperWrapper();
        ObjectMapper objectMapper = wrapper.getObjectMapper();
        JsonJacksonCodec jsonJacksonCodec = new JsonJacksonCodec(objectMapper);
        TypedJsonJacksonCodec typedJsonJacksonCodec = new TypedJsonJacksonCodec(Object.class, objectMapper);

        ObjectMapper objectMapper1 = jsonJacksonCodec.getObjectMapper();
        ObjectMapper objectMapper2 = typedJsonJacksonCodec.getObjectMapper();

        TestBean testBean=new TestBean();
        testBean.setDa(List.of("1","2"));
        System.out.println(objectMapper1.writeValueAsString(testBean));
        System.out.println(objectMapper2.writeValueAsString(testBean));
        System.out.println(objectMapper.writeValueAsString(testBean));
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TestBean {

        private List<String> da;
    }
}
