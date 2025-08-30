package cc.sofast.framework.starter.mybatis.mapper;

import cc.sofast.framework.starter.common.dto.PageParamQuery;
import com.github.yulichang.query.MPJQueryWrapper;

/**
 * @author wxl
 */
public interface ConditionQuery extends PageParamQuery {

    default PageParamQuery getPageParam() {
        return this;
    }

    default MPJQueryWrapper getJoinQueryWrapper() {
        return new MPJQueryWrapper();
    }
}
