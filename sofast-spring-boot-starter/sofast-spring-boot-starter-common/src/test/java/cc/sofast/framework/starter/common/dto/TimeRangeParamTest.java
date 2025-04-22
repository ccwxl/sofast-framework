package cc.sofast.framework.starter.common.dto;

import org.junit.jupiter.api.Test;

class TimeRangeParamTest {

    @Test
    public void testParser() {
        String query = "https://teset.sofast.cc?registerDate=2025-04-21 11:00:00.123,2025-04-22 11:00:00.123";
        String query2 = "https://teset.sofast.cc?test=1&registerDate=2025-04-21 11:00:00.123,2025-04-22 11:00:00.123";
        TimeRangeParam timeRangeParam = TimeRangeParam.parser(query);

    }
}