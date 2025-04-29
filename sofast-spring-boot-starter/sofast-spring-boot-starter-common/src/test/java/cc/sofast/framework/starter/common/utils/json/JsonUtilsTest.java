package cc.sofast.framework.starter.common.utils.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author wxl
 */
class JsonUtilsTest {

    @Test
    void test() {
        List<String> a = new ArrayList<>();
        a.add("1");

        JsonUtils.toJson(a);
    }
}