package cc.sofast.framework.starter.common.trans.dto.nesting;

import cc.sofast.framework.starter.common.trans.Trans;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Trans
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherDTO {

    private String id;
    @Trans(ref = "id")
    private String teacherName;

    @Trans
    private StudentDTO student;


    @Override
    public String toString() {
        return "TeacherDTO{" +
                "id='" + id + '\'' +
                ", teacherName='" + teacherName + '\'' +
                '}';
    }
}
