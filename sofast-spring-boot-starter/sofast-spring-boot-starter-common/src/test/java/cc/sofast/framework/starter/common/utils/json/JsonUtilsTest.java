package cc.sofast.framework.starter.common.utils.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author wxl
 */
class JsonUtilsTest {

    @Test
    void test() {
        ObjectMapper mapper = JsonUtils.json();
        assertNotNull(mapper);
    }
}