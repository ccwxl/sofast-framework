package cc.sofast.framework.starter.mybatis.searchbean;

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
    eq("eq"),
    /**
     * status=ne:ACTIVE
     */
    ne("ne"),
    /**
     * status=lt:ACTIVE
     */
    lt("lt"),
    /**
     * status=le:ACTIVE
     */
    le("le"),
    /**
     * status=gt:ACTIVE
     */
    gt("gt"),
    /**
     * status=ge:ACTIVE
     */
    ge("ge"),
    /**
     * status=like:ACTIVE
     */
    like("like"),
    /**
     * status=ll:ACTIVE
     */
    likeLeft("likeLeft"),
    /**
     * ?role=in:Manager,Engineer
     */
    in("in"),
    /**
     * ?role=lr:Manager,Engineer
     */
    likeRight("likeRight"),

    /**
     * ?role=bt:1,2
     */
    between("between");

    private final String expression;
}
