package cc.sofast.framework.starter.common.jackson;

import cc.sofast.framework.starter.common.enums.BaseEnum;
import cc.sofast.framework.starter.common.exception.ServiceException;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * 枚举转换
 *
 * @author wxl
 **/
public class EnumConverterFactory implements ConverterFactory<Object, BaseEnum> {
    private final Map<Class, Converter> converterCache = new WeakHashMap<>();

    @Override
    public <T extends BaseEnum> Converter<Object, T> getConverter(@NonNull Class<T> targetType) {
        return converterCache.computeIfAbsent(targetType,
                k -> converterCache.put(k, new EnumConverter(k))
        );
    }

    protected static class EnumConverter<T extends BaseEnum<T>> implements Converter<Object, T> {

        private final Class<T> enumType;

        public EnumConverter(@NonNull Class<T> enumType) {
            this.enumType = enumType;
        }

        @Override
        public T convert(@NonNull Object value) {
            return BaseEnum.of(this.enumType, value)
                    .orElseThrow(() -> new ServiceException("Contains illegal enumeration value"));
        }
    }
}
