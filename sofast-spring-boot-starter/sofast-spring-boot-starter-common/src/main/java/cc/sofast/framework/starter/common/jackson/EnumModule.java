package cc.sofast.framework.starter.common.jackson;

import cc.sofast.framework.starter.common.enums.BaseEnum;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import com.fasterxml.jackson.databind.module.SimpleDeserializers;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * 继承了BaseEnum接口的枚举值，将会统一按照以下格式序列化
 * {
 * "value": "foo",
 * "valueLabel": "bar"
 * }
 *
 * @author Zhu JW
 * @author wxl
 **/
@SuppressWarnings("all")
public class EnumModule extends SimpleModule {
    public EnumModule() {
        super("jacksonEnumTypeModule", Version.unknownVersion());
        this.setDeserializers(new CustomDeserializers());
        this.addSerializer(new EnumSerializer());
    }

    @SuppressWarnings("all")
    private static final class CustomDeserializers extends SimpleDeserializers {
        private CustomDeserializers() {
        }

        @Override
        public JsonDeserializer<?> findEnumDeserializer(Class<?> type, DeserializationConfig config,
                                                        BeanDescription beanDesc) throws JsonMappingException {
            // BaseEnum<?>，调用此序列化方法，否则使用 jackson 默认的序列化方法
            return BaseEnum.class.isAssignableFrom(type)
                    ? new EnumDeserializer(type)
                    : super.findEnumDeserializer(type, config, beanDesc);
        }

        private static final class EnumDeserializer<E extends BaseEnum<?>> extends StdScalarDeserializer<E> {
            private final Class<E> enumType;

            private EnumDeserializer(Class<E> clazz) {
                super(clazz);
                this.enumType = clazz;
            }

            @Override
            public E deserialize(JsonParser parser, DeserializationContext context) throws IOException {
                if (parser.getCurrentToken().isNumeric()) {
                    // 前端传递value
                    return BaseEnum.of(this.enumType, parser.getIntValue())
                            .orElseThrow(() -> new IllegalArgumentException("Unable to parse input value"));
                } else if (StringUtils.hasText(parser.getText())) {
                    // 前端传递label
                    return BaseEnum.of(this.enumType, parser.getText())
                            .orElseThrow(() -> new IllegalArgumentException("Unable to parse input value"));
                } else {
                    throw new IllegalArgumentException("Unable to parse input value 'cause wrong type");
                }
            }
        }
    }

    private static final class EnumSerializer extends StdSerializer<BaseEnum> {

        private EnumSerializer() {
            super(BaseEnum.class);
        }

        @Override
        public void serialize(BaseEnum data, JsonGenerator jsonGenerator, SerializerProvider provider)
                throws IOException {
            jsonGenerator.writeObject(data.getValue());
            jsonGenerator.writeFieldName(jsonGenerator.getOutputContext().getCurrentName() + "Label");
            jsonGenerator.writeString(data.getLabel());
        }
    }

}
