package cc.sofast.framework.starter.common.trans;

import cc.sofast.framework.starter.common.dto.TimeRangeParam;
import org.junit.jupiter.api.Test;

class TransUtilsTest {

    @Test
    void trans() {
//        List<Object> obj = new ArrayList<>();
//        obj.add(new TimeRangeParam());

//        TimeRangeParam obj = new TimeRangeParam();
//        TimeRangeParam[] obj = new TimeRangeParam[]{new TimeRangeParam()
//        };
        //
//        Queue<TimeRangeParam> obj =new ArrayDeque<>();
        TimeRangeParam obj = new TimeRangeParam();
        TransUtils.trans(obj);
    }
}