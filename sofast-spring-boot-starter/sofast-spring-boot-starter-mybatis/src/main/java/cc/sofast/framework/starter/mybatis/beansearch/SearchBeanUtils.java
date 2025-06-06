package cc.sofast.framework.starter.mybatis.beansearch;

import cc.sofast.framework.starter.common.dto.PageResult;
import cc.sofast.framework.starter.common.dto.SortableField;
import cc.sofast.framework.starter.common.dto.SortablePageParam;
import cc.sofast.framework.starter.common.utils.SpringUtils;
import cc.sofast.framework.starter.mybatis.utils.PageUtil;
import cn.zhxu.bs.BeanSearcher;
import cn.zhxu.bs.SearchResult;
import cn.zhxu.bs.util.MapBuilder;
import cn.zhxu.bs.util.MapUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author wxl
 */
public class SearchBeanUtils {

    public static Map<String, Object> queryParam(SortablePageParam page, Object query) {
        Class<?> queryConditionClass = query.getClass();
        List<BeanSearcherFiled> searcherFiledList = new ArrayList<>();
        ReflectionUtils.doWithFields(queryConditionClass, field -> {
            Where where = field.getAnnotation(Where.class);
            if (where != null) {
                field.setAccessible(true);
                Condition condition = where.value();
                String filedName = field.getName();
                Object filedVal = field.get(query);
                if (filedVal != null) {
                    searcherFiledList.add(new BeanSearcherFiled(condition, filedName, filedVal));
                }
            }
        });
        MapBuilder builder = MapUtils.builder();
        //组装分页
        builder.page(page.getPageIndex(), page.getPageSize());
        List<SortableField> sortingFields = page.getSortingFields();
        if (!CollectionUtils.isEmpty(sortingFields)) {
            //组装排序
            for (SortableField sortingField : sortingFields) {
                builder.orderBy(sortingField.getField(), sortingField.getOrder());
            }
        }
        //处理search条件
        for (BeanSearcherFiled searcherFiled : searcherFiledList) {
            builder.field(searcherFiled.filedName(), searcherFiled.getVal())
                    .op(searcherFiled.condition().getClazz());
        }
        return builder.build();
    }


    public static <T> PageResult<T> search(SortablePageParam page, Object queryParam, Class<T> result) {
        BeanSearcher beanSearcher = SpringUtils.getBean(BeanSearcher.class);
        Map<String, Object> queryParamMap = queryParam(page, queryParam);
        SearchResult<T> search = beanSearcher.search(result, queryParamMap);
        if (search != null) {
            return PageUtil.toPageResult(search, page.getPageIndex());
        }
        return new PageResult<>();
    }
}
