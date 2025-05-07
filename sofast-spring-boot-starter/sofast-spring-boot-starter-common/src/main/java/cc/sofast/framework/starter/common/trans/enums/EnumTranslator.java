package cc.sofast.framework.starter.common.trans.enums;

import cc.sofast.framework.starter.common.enums.BaseEnum;
import cc.sofast.framework.starter.common.trans.Translator;

import java.io.Serializable;
import java.util.Optional;

/**
 * @author wxl
 */
public class EnumTranslator<T extends Serializable> implements Translator<T, TransEnum> {

    @Override
    public String translator(T refVal, TransEnum annotation) {
        if (refVal == null) {
            return null;
        }

        if (refVal instanceof BaseEnum<?> && Enum.class.isAssignableFrom(refVal.getClass())) {
            return ((BaseEnum<?>) refVal).getLabel();
        }
        Class<?> enumType = annotation.typ();
        if (BaseEnum.class.isAssignableFrom(enumType) && Enum.class.isAssignableFrom(enumType)) {
            Class<? extends BaseEnum> s = (Class<? extends BaseEnum>) enumType;
            Optional<? extends BaseEnum> baseEnum = BaseEnum.of(s, refVal);
            return baseEnum.map(BaseEnum::getLabel).orElse(null);
        }
        return null;
    }
}
