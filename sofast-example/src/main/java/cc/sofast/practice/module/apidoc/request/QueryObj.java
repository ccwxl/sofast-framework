package cc.sofast.practice.module.apidoc.request;

import cc.sofast.framework.starter.common.dto.TimeRangeParam;
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
public class QueryObj {

    @Schema(type = "string", description = "时间范围")
    private TimeRangeParam timeRange;

    @Schema(type = "string", description = "年龄")
    private String age;
}
