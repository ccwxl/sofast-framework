package cc.sofast.framework.starter.mybatis.beansearch;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wxl
 */

@AllArgsConstructor
@Getter
public enum Condition {
    eq("eq"),
    ne("ne"),
    lt("lt"),
    le("le"),
    gt("gt"),
    ge("ge"),
    like("like"),
    likeLeft("likeLeft"),
    in("in"),
    likeRight("likeRight");

    private final String expression;
}
