package cc.sofast.framework.starter.beansearch;

import cn.zhxu.bs.FieldOp;
import cn.zhxu.bs.operator.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wxl
 */

@AllArgsConstructor
@Getter
public enum Condition {
    /**
     * status=eq:ACTIVE
     */
    eq("eq", Equal.class),
    /**
     * status=ne:ACTIVE
     */
    ne("ne", NotEqual.class),
    /**
     * status=lt:ACTIVE
     */
    lt("lt", LessThan.class),
    /**
     * status=le:ACTIVE
     */
    le("le", LessEqual.class),
    /**
     * status=gt:ACTIVE
     */
    gt("gt", GreaterThan.class),
    /**
     * status=ge:ACTIVE
     */
    ge("ge", GreaterEqual.class),
    /**
     * status=like:ACTIVE
     */
    like("like", Contain.class),
    /**
     * status=ll:ACTIVE
     */
    likeLeft("likeLeft", StartWith.class),
    /**
     * ?role=in:Manager,Engineer
     */
    in("in", InList.class),
    /**
     * ?role=lr:Manager,Engineer
     */
    likeRight("likeRight", EndWith.class),
    /**
     * ?role=bt:1,2
     */
    between("between", Between.class);

    private final String expression;
    private final Class<? extends FieldOp> clazz;
}
