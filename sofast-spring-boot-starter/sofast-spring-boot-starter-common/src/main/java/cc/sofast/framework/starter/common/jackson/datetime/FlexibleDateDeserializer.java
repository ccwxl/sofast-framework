package cc.sofast.framework.starter.common.jackson.datetime;

import cn.hutool.core.date.DateUtil;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.util.Date;

/**
 * @author wxl
 */
public class FlexibleDateDeserializer extends JsonDeserializer<Date> {

    @Override
    public Date deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String text = p.getText().trim();
        try {
            return DateUtil.parse(text).toJdkDate();
        } catch (Exception e) {
            throw new JsonParseException(p, "Unsupported date format: " + text);
        }
    }
}
