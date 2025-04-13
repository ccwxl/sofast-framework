package cc.sofast.framework.starter.redis.codec;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.List;

/**
 * @author wxl
 */
@Setter
@Getter
public class ObjectMapperWrapper {

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
     * 2. string 序列化不需要带双引号</br>
     * 2. 时间格式化成 yyyy-MM-dd HH:mm:ss</br>
     * 3. 忽略值为空的属性</br>
     * </p>
     */
    public static JsonMapper.Builder defaultBuilder() {
        SimpleModule module = new SimpleModule();
        module.addSerializer(String.class, new JsonSerializer<>() {
            @Override
            public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                // 直接写入，不加引号
                gen.writeRawValue(value);
            }
        });
        return JsonMapper.builder()
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .serializationInclusion(JsonInclude.Include.NON_NULL)
                .deactivateDefaultTyping()
                .visibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY)
                .addModule(module)
                .addModule(new ParameterNamesModule())
                .addModule(new Jdk8Module())
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
