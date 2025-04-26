package cc.sofast.framework.starter.web.converter;

import cc.sofast.framework.starter.common.dto.TimeRangeParam;
import cc.sofast.framework.starter.common.utils.ArrayUtils;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;

/**
 * 支持~ 到2025-09-10
 * 支持 2025-09-10 到~
 *
 * @author wxl
 */
public class TimeRangeParamConverter implements Converter<String, TimeRangeParam> {

    @Override
    public TimeRangeParam convert(String source) {
        var queryTime = source.split(",");
        if (ArrayUtils.isEmpty(queryTime) || queryTime.length != 2) {
            return null;
        }
        TimeRangeParam rangeParam = new TimeRangeParam();
        String startDateStr = ArrayUtils.get(queryTime, 0);
        String endDateStr = ArrayUtils.get(queryTime, 1);
        if ("~".equals(startDateStr)) {
            rangeParam.setStartTime(null);
        } else {
            LocalDateTime startDate = DateUtil.parse(startDateStr).toLocalDateTime();
            rangeParam.setStartTime(startDate);
        }
        if ("~".equals(endDateStr)) {
            rangeParam.setEndTime(null);
        } else {
            LocalDateTime endDate = DateUtil.parse(endDateStr).toLocalDateTime();
            rangeParam.setEndTime(endDate);
        }
        return rangeParam;
    }
}
