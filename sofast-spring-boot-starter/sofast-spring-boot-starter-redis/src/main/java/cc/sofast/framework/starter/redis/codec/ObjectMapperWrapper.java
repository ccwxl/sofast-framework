package cc.sofast.framework.starter.redis.codec;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.StreamReadFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import lombok.Getter;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author wxl
 */
@Setter
@Getter
public class ObjectMapperWrapper {

    private final static String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private static JsonMapper.Builder builder = defaultBuilder();

    private final ObjectMapper objectMapper;

    public ObjectMapperWrapper(List<ObjectMapperCustomizer> customizers) {
        for (ObjectMapperCustomizer customizer : customizers) {
            customizer.customize(builder);
        }
        this.objectMapper = builder.build();
    }

    public ObjectMapperWrapper() {
        this.objectMapper = builder.build();
    }

    /**
     * 创建一个默认配置的 Builder
     * <p>
     * 1. 保持long不要序列化成string</br>
     * 2. 时间格式化成 yyyy-MM-dd HH:mm:ss</br>
     * 3. 忽略值为空的属性</br>
     * </p>
     */
    public static JsonMapper.Builder defaultBuilder() {

        return JsonMapper.builder()
                .defaultDateFormat(new SimpleDateFormat(DEFAULT_DATE_FORMAT))
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
                // 支持时间类型
                .addModule(new JavaTimeModule());
    }

    /**
     * 设置自定义的 builder
     *
     * @param builder builder
     */
    public static void setBuilder(JsonMapper.Builder builder) {
        ObjectMapperWrapper.builder = builder;
    }
}
