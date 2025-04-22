package cc.sofast.framework.starter.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDateTime;

/**
 * ?registerDate=2020,2022
 * ?registerDate=2020-01,2020-02
 * ?registerDate=2020-01-01,2020-02-02
 * ?registerDate=2020-01-01 12:11:11,2020-02-02 13:11:11
 * ?registerDate=2020-01-01 12:11:11.123,2020-02-02 13:11:11.2334
 *
 * @author wxl
 */
@Data
@FieldNameConstants
@AllArgsConstructor
@NoArgsConstructor
public class TimeRangeParam {
    /**
     * 开始时间
     */
    private LocalDateTime startTime;
    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    public static TimeRangeParam parser(String query) {

        return null;
    }
}
