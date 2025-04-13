package cc.sofast.framework.starter.common.utils.json;

import cc.sofast.framework.starter.common.jackson.EnumModule;
import cc.sofast.framework.starter.common.jackson.JacksonModule;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
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
                // 序列化时忽略值为空的属性
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
                // 反序列化时支持null和空串
                .configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true)
                // 反序列化时忽略未知属性
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                // 允许单引号
                .configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true)
                //输出所有字段
                .serializationInclusion(JsonInclude.Include.ALWAYS)
                // 没有默认构造函数或无注解的构造函数参数 情况下，正确地反序列化 Java 对象
                .addModule(new ParameterNamesModule())
                .addModule(new Jdk8Module())
                .addModule(new JavaTimeModule())
                .addModule(new JacksonModule())
                .addModule(new EnumModule())
                .findAndAddModules();
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
