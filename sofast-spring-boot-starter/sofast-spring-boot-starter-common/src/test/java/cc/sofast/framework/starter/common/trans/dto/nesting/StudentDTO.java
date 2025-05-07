package cc.sofast.framework.starter.common.trans.dto.nesting;

import cc.sofast.framework.starter.common.trans.Trans;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Trans
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO {
    private String id;
    @Trans(ref = "id")
    private String studentName;

    @Trans
    private TeacherDTO teacher;


    @Override
    public String toString() {
        return "StudentDTO{" +
                "id='" + id + '\'' +
                ", studentName='" + studentName + '\'' +
                '}';
    }
}
