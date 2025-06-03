package cc.sofast.framework.starter.mybatis.searchbean;

import cc.sofast.framework.starter.common.dto.PageParam;
import cc.sofast.framework.starter.common.dto.PageResult;
import cc.sofast.framework.starter.common.utils.SpringUtils;
import cc.sofast.framework.starter.mybatis.utils.PageUtil;
import cn.zhxu.bs.BeanSearcher;
import cn.zhxu.bs.SearchResult;
import cn.zhxu.bs.operator.Equal;
import cn.zhxu.bs.util.MapUtils;

import java.util.Map;

/**
 * @author wxl
 */
public class SearchBeanUtils {

    public static Map<String, Object> queryParam(PageParam page, Object query) {
        Map<String, Object> queryParamMap = MapUtils.builder()
                .field("a", "a").op(Equal.class).ic()
                .build();
        return queryParamMap;
    }

    public static <T> PageResult<T> search(PageParam page, Object queryParam, Class<T> result) {
        BeanSearcher beanSearcher = SpringUtils.getBean(BeanSearcher.class);
        Map<String, Object> queryParamMap = queryParam(page, queryParam);
        SearchResult<T> search = beanSearcher.search(result, queryParamMap);
        if (search != null) {
            return PageUtil.toPageResult(search, page.getPageIndex());
        }
        return new PageResult<>();
    }
}
