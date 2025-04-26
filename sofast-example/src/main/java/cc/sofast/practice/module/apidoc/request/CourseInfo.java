package cc.sofast.practice.module.apidoc.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    private Long ts;

    private List<String> data2;

    private Map<String, Object> maps;

//    private List<CourseType> courses;

    private Date dates;

    private LocalDate localDates;

    private LocalDateTime localDateTime;
}
