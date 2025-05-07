package cc.sofast.framework.starter.common.trans;

import cc.sofast.framework.starter.common.enums.ColorEnum;
import cc.sofast.framework.starter.common.trans.dict.DictTranslator;
import cc.sofast.framework.starter.common.trans.dict.DictTranslatorService;
import cc.sofast.framework.starter.common.trans.dto.TransUserDTO;
import cc.sofast.framework.starter.common.trans.enums.EnumTranslator;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
class TransTypeTest {

    @Test
    void transBean() {
        InMemoryKvTransStore kvTransStore = new InMemoryKvTransStore();
        kvTransStore.put("1", "sofast");
        DictTranslatorService dictTranslatorService = (group, key) -> {
            if (group.equals("sex")) {
                if (key.equals("1")) {
                    return "男";
                }
                if (key.equals("2")) {
                    return "女";
                }
            }
            return "default";
        };

        DictTranslator dictTranslator = new DictTranslator(dictTranslatorService);
        KvTranslator kvTranslator = new KvTranslator(kvTransStore);
        EnumTranslator enumTranslator = new EnumTranslator();

        TranslatorFactory.registerTranslator(kvTranslator);
        TranslatorFactory.registerTranslator(enumTranslator);
        TranslatorFactory.registerTranslator(dictTranslator);

        TransUserDTO userDTO = new TransUserDTO();
        userDTO.setId(1L);
        userDTO.setColor(ColorEnum.YELLOW);
        userDTO.setDict("1");

        TransUtils.trans(userDTO);
        Assertions.assertEquals("sofast", userDTO.getName());
        Assertions.assertEquals("黄", userDTO.getColorName());
        Assertions.assertEquals("男", userDTO.getDictName());
        log.info("{}", userDTO);
    }

    @Test
    void transList() {
    }

    @Test
    void transMap() {
    }

    @Test
    void transEnum() {
    }
}