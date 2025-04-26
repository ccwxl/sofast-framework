package cc.sofast.framework.starter.web.converter;

import cn.hutool.core.date.DateUtil;
import org.springframework.core.convert.converter.Converter;

import java.util.Date;

/**
 * @author wxl
 */
public class StringToDateConverter implements Converter<String, Date> {

    @Override
    public Date convert(String source) {

        return DateUtil.parse(source);
    }
}
