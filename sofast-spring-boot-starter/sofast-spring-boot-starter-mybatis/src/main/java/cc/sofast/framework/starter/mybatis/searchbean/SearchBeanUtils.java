package cc.sofast.framework.starter.mybatis.searchbean;

import cc.sofast.framework.starter.common.dto.PageParam;
import cc.sofast.framework.starter.common.dto.PageResult;
import cc.sofast.framework.starter.common.utils.SpringUtils;
import cn.zhxu.bs.BeanSearcher;

import java.util.Map;

/**
 * @author wxl
 */
public class SearchBeanUtils {

    public static Map<String, Object> queryParam(PageParam page, Object query) {

        return null;
    }

    public static <T> PageResult<T> search(PageParam page, Object queryParam, Class<T> result) {
        BeanSearcher beanSearcher = SpringUtils.getBean(BeanSearcher.class);

        return null;
    }
}
