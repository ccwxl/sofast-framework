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
import java.util.function.Consumer;

/**
 * @author wxl
 */
@Slf4j
public class JsonUtils {

    private static ObjectMapper MAPPER = defaultBuilder().build();

    private JsonUtils() {
    }

    /**
     * 创建一个默认配置的 Builder
     */
    public static JsonMapper.Builder defaultBuilder() {
        return JsonMapper.builder()
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .serializationInclusion(JsonInclude.Include.NON_NULL)
                .addModule(new JavaTimeModule());
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

    /**
     * 用于允许外部设置自定义 ObjectMapper
     */
    public static void setCustomMapper(ObjectMapper customMapper) {
        if (customMapper != null) {
            MAPPER = customMapper;
        }
    }

    /**
     * 使用 Lambda 自定义 builder
     */
    public static void setCustomMapper(Consumer<JsonMapper.Builder> config) {
        JsonMapper.Builder builder = defaultBuilder();
        config.accept(builder);
        JsonMapper customerMapper = builder.build();
        setCustomMapper(customerMapper);
    }
}
