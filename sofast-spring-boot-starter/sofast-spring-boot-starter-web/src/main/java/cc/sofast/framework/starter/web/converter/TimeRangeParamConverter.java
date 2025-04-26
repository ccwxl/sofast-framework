package cc.sofast.framework.starter.web.converter;

import cc.sofast.framework.starter.common.dto.TimeRangeParam;
import cc.sofast.framework.starter.common.utils.ArrayUtils;
import org.springframework.core.convert.converter.Converter;

/**
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
        String startDate = ArrayUtils.get(queryTime, 0);
        String endDate = ArrayUtils.get(queryTime, 1);

        //支持~ 到2025-09-10
        //支持 2025-09-10 到~
        return new TimeRangeParam();
    }
}
