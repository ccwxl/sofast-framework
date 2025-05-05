package cc.sofast.framework.starter.common.trans.dto;

import cc.sofast.framework.starter.common.enums.ColorEnum;
import cc.sofast.framework.starter.common.trans.Trans;
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
public class TransUserDTO {

    //simple
    private Long id;
    private String name;

    //enum
    private ColorEnum color;
    private String colorName;


    //dict
    private String dict;
    private String dictName;
}
