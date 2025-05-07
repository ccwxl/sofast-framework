package cc.sofast.framework.starter.common.trans.dto;

import cc.sofast.framework.starter.common.enums.ColorEnum;
import cc.sofast.framework.starter.common.trans.Trans;
import cc.sofast.framework.starter.common.trans.dict.TransDict;
import cc.sofast.framework.starter.common.trans.enums.TransEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wxl
 */
@Data
@Trans
@AllArgsConstructor
@NoArgsConstructor
public class SampleDTO {

    //simple
    private Long id;
    @Trans(ref = "id")
    private String name;

    //enum
    private ColorEnum color;
    @TransEnum(ref = "color", typ = ColorEnum.class)
    private String colorName;


    //dict
    private String dict;
    @TransDict(ref = "dict", group = "sex")
    private String dictName;
}
