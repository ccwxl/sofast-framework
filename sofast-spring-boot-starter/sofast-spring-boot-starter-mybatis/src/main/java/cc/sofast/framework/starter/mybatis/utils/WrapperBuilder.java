package cc.sofast.framework.starter.mybatis.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.github.yulichang.query.MPJQueryWrapper;

/**
 * @author wxl
 */
public class WrapperBuilder {

    public static <T> QueryWrapper<T> build(Object condition, Class<T> entityClass) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        queryWrapper.setEntityClass(entityClass);
        return queryWrapper;
    }

    public static <DTO, LEFT, RIGHT> MPJQueryWrapper<DTO> buildJoin(Object condition, DTO dto,
                                                                    SFunction<LEFT, ?> left, SFunction<RIGHT, ?> right) {
        MPJQueryWrapper<DTO> queryWrapper = new MPJQueryWrapper<DTO>();
        return queryWrapper;
    }
}
