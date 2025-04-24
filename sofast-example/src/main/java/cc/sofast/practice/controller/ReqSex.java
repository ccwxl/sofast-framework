package cc.sofast.practice.controller;

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
@Schema(description = "请求性别")
public class ReqSex {

    @Schema(description = "年龄")
    private String age;

    @Schema(description = "性别")
    private SexEnum sex;
}
