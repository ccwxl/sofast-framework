package cc.sofast.framework.starter.common.utils.json;

import cc.sofast.framework.starter.common.constant.SofastConstant;
import cc.sofast.framework.starter.common.jackson.EnumModule;
import cc.sofast.framework.starter.common.jackson.JacksonModule;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.StreamReadFeature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Map;
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
                .defaultDateFormat(new SimpleDateFormat(SofastConstant.Jackson.DATE_TIME_FORMAT))
                // 去掉默认的时间戳格式
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                // 字段排序
                .configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true)
                // 当序列化对象时如果字段为空则不进行序列化,不要报错
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
                // 当反序列化时对象没有此属性石则忽略未知属性,不要报错
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                // 当启用时反序列化时空JSON字符串可以被视为等同于JSON的null
                .configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true)
                // 允许单引号
                .configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true)
                // 允许key不带引号
                .configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true)
                //但序列化出错时,定位到出错的哪个位置
                .enable(StreamReadFeature.INCLUDE_SOURCE_IN_LOCATION)
                // 表示只包括具有非空值的属性参与序列化和反序列化。
                .serializationInclusion(JsonInclude.Include.NON_NULL)
                // 关闭默认的类型信息，默认情况下，Jackson会在序列化时在JSON中包含 fully qualified class name，以便在序列化时还原该对象。
                .deactivateDefaultTyping()
                // 设置属性可见性
                .visibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY)
                // 支持构造函数注入
                .addModule(new ParameterNamesModule())
                // 支持Java8
                .addModule(new Jdk8Module())
                // 自定义的枚举
                .addModule(new EnumModule())
                // 支持时间类型
                .addModule(new JavaTimeModule())
                // 自定义规则
                .addModule(new JacksonModule());
    }

    public static String toJson(Object obj) {
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

    public static <T> T toObj(String json, Class<T> clazz) {
        try {
            return MAPPER.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            log.error("json deserialization failed", e);
            throw new RuntimeException(e);
        }
    }

    public static <T> T toObj(String json, TypeReference<T> typeReference) {
        try {
            return MAPPER.readValue(json, typeReference);
        } catch (JsonProcessingException e) {
            log.error("json deserialization failed", e);
            throw new RuntimeException(e);
        }
    }

    public static <T> T toObj(String json, Type type) {
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

    /**
     * convertValue 方法
     */
    public static <T> T convertValue(Object value, TypeReference<T> typeReference) {
        return MAPPER.convertValue(value, typeReference);
    }

    /**
     * convertValue 方法
     */
    public static <T> T convertValue(Object value, Class<T> typeReference) {
        return MAPPER.convertValue(value, typeReference);
    }

    /**
     * convertValue 方法
     */
    public static <T> T convertValue(Object value, JavaType typeReference) {
        return MAPPER.convertValue(value, typeReference);
    }

    /**
     * convertValue 方法
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> toMap(Object value) {
        return MAPPER.convertValue(value, Map.class);
    }
}
