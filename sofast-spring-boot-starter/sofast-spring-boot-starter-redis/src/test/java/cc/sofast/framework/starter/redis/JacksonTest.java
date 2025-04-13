package cc.sofast.framework.starter.redis;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wxl
 */
public class JacksonTest {

    @Test
    public void testFileName() throws JsonProcessingException {
        Map<String, String> shareImages = new HashMap<>();
        shareImages.put("fileName", "test");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(JsonGenerator.Feature.QUOTE_FIELD_NAMES, false);
        String items = objectMapper.writeValueAsString(shareImages);
        System.out.println(items);
    }
}
