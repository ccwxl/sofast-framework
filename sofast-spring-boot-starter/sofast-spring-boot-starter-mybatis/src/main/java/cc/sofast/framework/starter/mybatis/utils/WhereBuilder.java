package cc.sofast.framework.starter.mybatis.utils;

import cc.sofast.framework.starter.mybatis.beansearch.Condition;
import cc.sofast.framework.starter.mybatis.beansearch.Where;
import com.baomidou.mybatisplus.core.conditions.interfaces.Compare;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;


/**
 * @author wxl
 */
@Slf4j
public class WhereBuilder {

    private WhereBuilder() {
    }

    public static <T, R> QueryWrapper<R> build(T t, R r) {
        QueryWrapper<R> queryWrapper = new QueryWrapper();
        if (t == null) {
            return queryWrapper;
        }

        Class<?> tc = t.getClass();
        Stream.of(tc.getDeclaredFields())
                .peek(field -> field.setAccessible(true))
                .filter(field -> {
                    try {
                        if (field.get(t) instanceof String) {
                            return StringUtils.isNotBlank((String) field.get(t)) && field.getAnnotation(Where.class) != null;
                        }
                        return field.get(t) != null && field.getAnnotation(Where.class) != null;
                    } catch (IllegalAccessException e) {
                        log.error("获取字段时异常:", e);
                        return false;
                    }
                }).forEach(field -> {
                    try {
                        Where where = field.getAnnotation(Where.class);
                        Condition condition = where.value();
                        String column = where.column();
                        Object value = field.get(t);
                        WHERE_MAP.get(condition).invoke(queryWrapper, column, value);
                    } catch (Exception e) {
                        log.error("执行条件拼接时异常:", e);
                    }
                });
        return queryWrapper;
    }

    private static final Map<Condition, Con3<QueryWrapper<?>, String, Object>> WHERE_MAP = new HashMap<>() {{
        put(Condition.eq, Compare::eq);
        put(Condition.ne, Compare::ne);
        put(Condition.lt, Compare::lt);
        put(Condition.le, Compare::le);
        put(Condition.gt, Compare::gt);
        put(Condition.ge, Compare::ge);
        put(Condition.like, Compare::like);
        put(Condition.likeLeft, Compare::likeLeft);
        put(Condition.likeRight, Compare::likeRight);
        put(Condition.in, (queryWrapper, column, value) -> queryWrapper.in(column, ((List<Object>) value).toArray()));
    }};
}

@FunctionalInterface
interface Con3<P1, P2, P3> {
    void invoke(P1 p1, P2 p2, P3 p3);
}
