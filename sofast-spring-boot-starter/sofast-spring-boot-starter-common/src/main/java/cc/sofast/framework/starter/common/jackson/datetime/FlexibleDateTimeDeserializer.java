package cc.sofast.framework.starter.common.jackson.datetime;

import cn.hutool.core.date.DateUtil;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * 支持多种格式的反序列化
 * @author wxl
 */
public class FlexibleDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

    @Override
    public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String text = p.getText().trim();
        try {
            return DateUtil.parse(text).toLocalDateTime();
        } catch (Exception e) {
            throw new JsonParseException(p, "Unsupported date format: " + text);
        }
    }
}