package cc.sofast.practice.module.trans.res;

import cc.sofast.framework.starter.common.trans.Trans;
import cc.sofast.framework.starter.common.trans.enums.TransEnum;
import cc.sofast.practice.module.apidoc.request.CourseType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

/**
 * @author wxl
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants
public class TransResponse {

    private String id;
    @Trans(value = "id")
    private String name;

    /**
     * jackson会自动处理增加一个label字段
     */
    private CourseType courseType;

    private String courseStr;
    @TransEnum(typ = CourseType.class, ref = Fields.courseStr)
    private String courseStrName;


}
