package cc.sofast.framework.starter.web.converter;

import cc.sofast.framework.starter.common.enums.BaseEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

/**
 * @author wxl
 */
@SuppressWarnings("all")
public class StringToEnumConverterFactory implements ConverterFactory<String, Enum> {

    @Override
    public <T extends Enum> Converter<String, T> getConverter(Class<T> targetType) {
        return new StringToEnumConverterFactory.StringToEnum(getEnumType(targetType));
    }


    private static class StringToEnum<T extends Enum> implements Converter<String, T> {

        private final Class<T> enumType;

        StringToEnum(Class<T> enumType) {
            this.enumType = enumType;
        }

        @Override
        @Nullable
        public T convert(String source) {
            if (source.isEmpty()) {
                // It's an empty enum identifier: reset the enum value to null.
                return null;
            }
            if (BaseEnum.class.isAssignableFrom(this.enumType) && Enum.class.isAssignableFrom(this.enumType)) {
                Class<? extends BaseEnum> s = (Class<? extends BaseEnum>) enumType;
                return BaseEnum.of(s, source)
                        .map(anEnum -> (T) anEnum)
                        .orElseGet(() -> (T) Enum.valueOf(this.enumType, source.trim().toUpperCase()));
            }
            return (T) Enum.valueOf(this.enumType, source.trim().toUpperCase());
        }
    }

    public static Class<?> getEnumType(Class<?> targetType) {
        Class<?> enumType = targetType;
        while (enumType != null && !enumType.isEnum()) {
            enumType = enumType.getSuperclass();
        }
        Assert.notNull(enumType, () -> "The target type " + targetType.getName() + " does not refer to an enum");
        return enumType;
    }
}