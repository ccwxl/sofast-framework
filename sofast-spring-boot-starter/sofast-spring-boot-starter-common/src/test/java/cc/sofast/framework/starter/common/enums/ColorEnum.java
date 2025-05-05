package cc.sofast.framework.starter.common.enums;

/**
 * @author wxl
 */
public enum ColorEnum implements BaseEnum<Integer> {
    YELLOW(1, "黄"),
    RED(2, "红"),
    GREED(3, "绿");

    final Integer value;
    final String msg;

    ColorEnum(Integer value, String msg) {
        this.value = value;
        this.msg = msg;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public String getLabel() {
        return msg;
    }
}