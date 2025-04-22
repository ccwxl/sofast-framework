package cc.sofast.framework.starter.common.enums;

import cc.sofast.framework.starter.common.exception.ServiceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BaseEnumTest {

    @Test
    public void testEnum() {
        String YELLOW = "YELLOW";
        String yellow = "yellow";
        Integer value = 1;
        ColorEnum c1 = BaseEnum.of(ColorEnum.class, YELLOW).orElseThrow(() -> new ServiceException("YELLOW Contains illegal enumeration value"));
        ColorEnum c2 = BaseEnum.of(ColorEnum.class, yellow).orElseThrow(() -> new ServiceException("yellow Contains illegal enumeration value"));
        ColorEnum c3 = BaseEnum.of(ColorEnum.class, value).orElseThrow(() -> new ServiceException("value Contains illegal enumeration value"));
        Assertions.assertEquals(ColorEnum.YELLOW, c1);
        Assertions.assertEquals(ColorEnum.YELLOW, c2);
        Assertions.assertEquals(ColorEnum.YELLOW, c3);
    }

}