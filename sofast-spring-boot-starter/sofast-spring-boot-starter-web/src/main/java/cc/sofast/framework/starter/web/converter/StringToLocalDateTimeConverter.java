package cc.sofast.framework.starter.web.converter;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author wxl
 */
public class StringToLocalDateTimeConverter implements Converter<String, LocalDateTime> {
    @Override
    public LocalDateTime convert(String source) {
        try {
            DateTime dateTime = DateUtil.parse(source);
            if (dateTime != null) {
                return dateTime.toLocalDateTime();
            }
        } catch (Exception e) {
            return LocalDateTime.parse(source, DateTimeFormatter.ISO_DATE_TIME);
        }
        return LocalDateTime.parse(source, DateTimeFormatter.ISO_DATE_TIME);
    }
}
