package cc.sofast.framework.starter.common.trans;

import cc.sofast.framework.starter.common.enums.ColorEnum;
import cc.sofast.framework.starter.common.trans.dict.DictTranslator;
import cc.sofast.framework.starter.common.trans.dict.DictTranslatorService;
import cc.sofast.framework.starter.common.trans.dto.SampleDTO;
import cc.sofast.framework.starter.common.trans.dto.nesting.StudentDTO;
import cc.sofast.framework.starter.common.trans.dto.nesting.TeacherDTO;
import cc.sofast.framework.starter.common.trans.enums.EnumTranslator;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

@Slf4j
class TransTypeTest {

    @BeforeAll
    public static void init() {
        InMemoryKvTransStore kvTransStore = new InMemoryKvTransStore();
        kvTransStore.put("1", "sofast");
        kvTransStore.put("2", "studentNameLab");
        kvTransStore.put("3", "teacherNameLab");
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
    }

    @Test
    void transNesting() {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setId("2");
        TeacherDTO teacherDTO = new TeacherDTO();
        teacherDTO.setId("3");
        studentDTO.setTeacher(teacherDTO);
        teacherDTO.setStudent(studentDTO);

        TransUtils.trans(studentDTO);
        log.info("{}", studentDTO);
        log.info("{}", teacherDTO);
    }

    @Test
    void transBean() {
        SampleDTO userDTO = new SampleDTO();
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