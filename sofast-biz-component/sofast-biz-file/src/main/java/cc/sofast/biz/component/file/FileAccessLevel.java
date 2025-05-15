package cc.sofast.biz.component.file;

import cc.sofast.framework.starter.common.enums.BaseEnum;
import lombok.Getter;

/**
 * @author wxl
 */
@Getter
public enum FileAccessLevel implements BaseEnum<String> {

    PUBLIC("public", "共有"),
    PRIVATE("private", "私有"),
    ;

    private final String code;
    private final String desc;

    FileAccessLevel(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }


    @Override
    public String getValue() {
        return code;
    }

    @Override
    public String getLabel() {
        return desc;
    }
}
