package cc.sofast.practice.module.apidoc.request;

import cc.sofast.framework.starter.common.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wxl
 */

@AllArgsConstructor
@Getter
public enum CourseType implements BaseEnum<Long> {
    MATH(1, "数学"),
    ENGLISH(2, "英语"),
    CHINESE(3, "语文"),
    COMPUTER(4, "计算机");
    /**
     * 课程编码
     */
    final int code;
    /**
     * 课程标签
     */
    final String label;


    @Override
    public Long getValue() {
        return (long) code;
    }

    @Override
    public String getLabel() {
        return label;
    }
}
