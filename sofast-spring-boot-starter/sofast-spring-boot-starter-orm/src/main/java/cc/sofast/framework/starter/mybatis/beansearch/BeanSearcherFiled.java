package cc.sofast.framework.starter.mybatis.beansearch;

import cn.zhxu.bs.FieldOp;
import cn.zhxu.bs.operator.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @author wxl
 */
public record BeanSearcherFiled(Condition condition, String filedName, Object filedVal) {

    private static final Map<Class<? extends FieldOp>, Function<Object, Object>> CLASS_FUNCTION_MAP = new HashMap<>();

    static {
        //TODO 待完善
        CLASS_FUNCTION_MAP.put(Equal.class, f -> f);
        CLASS_FUNCTION_MAP.put(NotEqual.class, f -> f);
        CLASS_FUNCTION_MAP.put(LessThan.class, f -> f);
        CLASS_FUNCTION_MAP.put(LessEqual.class, f -> f);
        CLASS_FUNCTION_MAP.put(GreaterThan.class, f -> f);
        CLASS_FUNCTION_MAP.put(GreaterEqual.class, f -> f);
        CLASS_FUNCTION_MAP.put(Contain.class, f -> f);
        CLASS_FUNCTION_MAP.put(StartWith.class, f -> f);
        CLASS_FUNCTION_MAP.put(EndWith.class, f -> f);
        CLASS_FUNCTION_MAP.put(InList.class, f -> f);
        CLASS_FUNCTION_MAP.put(Between.class, f -> f);
    }

    public Object getVal() {
        Class<? extends FieldOp> clazz = condition.getClazz();
        Function<Object, Object> objectObjectFunction = CLASS_FUNCTION_MAP.get(clazz);
        if (objectObjectFunction != null) {
            return objectObjectFunction.apply(filedVal);
        }
        throw new RuntimeException("不支持的查询条件");
    }
}
