package cc.sofast.framework.starter.common.utils.json;

import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wxl
 */
@Slf4j
public class TestJsonUtilsSpi implements JsonUtilsSpi {

    @Override
    public void customer(JsonMapper.Builder builder) {
        log.info("customer objectMapper");
    }
}
