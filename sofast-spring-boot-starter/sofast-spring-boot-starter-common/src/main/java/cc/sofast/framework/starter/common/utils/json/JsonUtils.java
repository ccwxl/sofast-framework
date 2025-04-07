package cc.sofast.framework.starter.common.utils.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.lang.reflect.Type;
import java.util.ServiceLoader;

/**
 * @author wxl
 */
public class JsonUtils {

    private static final JsonMapper.Builder BUILDER = JsonMapper.builder();

    private static final ObjectMapper MAPPER;

    private JsonUtils() {
    }

    static {
        BUILDER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        BUILDER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        BUILDER.serializationInclusion(JsonInclude.Include.NON_NULL);
        BUILDER.addModule(new JavaTimeModule());
        ServiceLoader<JsonUtilsSpi> customers = ServiceLoader.load(JsonUtilsSpi.class);
        for (JsonUtilsSpi customer : customers) {
            customer.customer(BUILDER);
        }
        MAPPER = BUILDER.build();
    }

    public String toJson(Object obj) {
        try {
            return MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T toObj(String json, Class<T> clazz) {
        try {
            return MAPPER.readValue(json, new TypeReference<T>() {
                @Override
                public Type getType() {
                    return clazz;
                }
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static ObjectMapper getMapper() {
        return MAPPER;
    }
}
