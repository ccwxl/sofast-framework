package cc.sofast.framework.starter.common.utils.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Type;
import java.util.ServiceLoader;

/**
 * @author wxl
 */
@Slf4j
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
            log.error("json serialization failed", e);
            throw new RuntimeException(e);
        }
    }

    public static byte[] toJsonByte(Object object) {
        try {
            return MAPPER.writeValueAsBytes(object);
        } catch (JsonProcessingException e) {
            log.error("json serialization failed", e);
            throw new RuntimeException(e);
        }
    }

    public static String toJsonPrettyString(Object object) {
        try {
            return MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("json serialization failed", e);
            throw new RuntimeException(e);
        }
    }

    public <T> T toObj(String json, Class<T> clazz) {
        try {
            return MAPPER.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            log.error("json deserialization failed", e);
            throw new RuntimeException(e);
        }
    }

    public <T> T toObj(String json, TypeReference<T> typeReference) {
        try {
            return MAPPER.readValue(json, typeReference);
        } catch (JsonProcessingException e) {
            log.error("json deserialization failed", e);
            throw new RuntimeException(e);
        }
    }

    public <T> T toObj(String json, Type type) {
        try {
            return MAPPER.readValue(json, MAPPER.constructType(type));
        } catch (JsonProcessingException e) {
            log.error("json deserialization failed", e);
            throw new RuntimeException(e);
        }
    }

    public static ObjectMapper json() {
        return MAPPER;
    }
}
