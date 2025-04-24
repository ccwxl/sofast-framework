package cc.sofast.practice.module.apidoc.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wxl
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "课程信息")
public class CourseInfo {

    @Schema(description = "学生")
    private String student;

    @Schema(description = "分数")
    public Integer score;

    @Schema(description = "课程类别")
    private CourseType course;
}
