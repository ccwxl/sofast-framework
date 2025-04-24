package cc.sofast.framework.starter.knife4j;

import cc.sofast.framework.starter.common.enums.BaseEnum;
import com.fasterxml.jackson.databind.type.SimpleType;
import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.Parameter;
import org.springdoc.core.customizers.ParameterCustomizer;
import org.springdoc.core.customizers.PropertyCustomizer;
import org.springframework.core.MethodParameter;

import java.util.Arrays;
import java.util.Objects;

/**
 * HelioBaseEnum子枚举增强
 * <p>
 * 示例代码：<br />
 * {@code @Schema(description} = "性别")<br />
 * private GenderEnum gender;
 * <p>
 * 输出文档描述：统一使用名称描述
 * 性别(UNKNOWN=未知 MAN=男 WOMAN=女)
 *
 * @author wxl
 */
public class BaseEnumCustomizer implements PropertyCustomizer, ParameterCustomizer {

    /**
     * 主要用于 @Schema 注解标记的字段
     */
    @Override
    public Schema<?> customize(Schema property, AnnotatedType propertyType) {
        if (Objects.nonNull(property) && Objects.nonNull(propertyType)) {
            try {
                SimpleType simpleType = (SimpleType) propertyType.getType();
                if (BaseEnum.class.isAssignableFrom(simpleType.getRawClass())) {
                    property.setDescription(determineDescription(simpleType.getRawClass(), property.getDescription()));
                }
            } catch (Exception e) {
                // fail to customize, ignored
            }
        }
        return property;
    }

    /**
     * 主要用于 @Parameter 注解标记的字段，以及路径参数、Query
     */
    @Override
    public Parameter customize(Parameter parameterModel, MethodParameter methodParameter) {
        if (Objects.nonNull(parameterModel) && Objects.nonNull(methodParameter)
                && BaseEnum.class.isAssignableFrom(methodParameter.getParameterType())) {
            String des = determineDescription(methodParameter.getParameterType(), parameterModel.getDescription());
            parameterModel.setDescription(des);
            Schema schema = parameterModel.getSchema();
            if (Objects.nonNull(schema)) {
                schema.setDescription(des);
                schema.setType("string");
                Class<?> parameterType = methodParameter.getParameterType();
                BaseEnum<?>[] enumItems = (BaseEnum<?>[]) parameterType.getEnumConstants();
                schema.setEnum(Arrays.stream(enumItems).map(s -> {
                    if (s instanceof Enum<?> e) {
                        return e.name();
                    }
                    return null;
                }).filter(Objects::nonNull).toList());
            }
        }
        return parameterModel;
    }

    /**
     * 确定输出的描述文本
     */
    protected String determineDescription(Class<?> helioBaseEnum, String originalDescription) {
        // 获取枚举类中的所有枚举项
        BaseEnum<?>[] enumItems = (BaseEnum<?>[]) helioBaseEnum.getEnumConstants();

        // 拼接描述字符串
        StringBuilder newDescription = new StringBuilder(128);
        if (originalDescription != null) {
            newDescription.append(originalDescription);
        }
        newDescription.append('(');

        for (int i = 0; i < enumItems.length; i++) {
            BaseEnum<?> enumItem = enumItems[i];
            if (enumItem instanceof Enum<?> e) {
                newDescription
                        .append(e.name().toUpperCase())
                        .append('=')
                        .append(enumItems[i].getLabel());

                if (i < enumItems.length - 1) {
                    // 不是最后一项，增加分割符
                    newDescription.append(' ');
                }
            }
        }
        newDescription.append(")<br />");

        return newDescription.toString();
    }
}
