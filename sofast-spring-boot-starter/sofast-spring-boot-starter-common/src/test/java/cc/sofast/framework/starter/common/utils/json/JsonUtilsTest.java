package cc.sofast.framework.starter.common.utils.json;

import cc.sofast.framework.starter.common.dto.TimeRangeParam;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wxl
 */
class JsonUtilsTest {

    @Test
    void test() {
//        List<Object> a = new ArrayList<>();
//        a.add(new TimeRangeParam());

        Map<String, Object> a = new HashMap<>();
        a.put("a", new TimeRangeParam());
        JsonUtils.toJson(a);
    }
}