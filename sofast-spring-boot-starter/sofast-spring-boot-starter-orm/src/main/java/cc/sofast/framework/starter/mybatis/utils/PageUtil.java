package cc.sofast.framework.starter.mybatis.utils;

import cc.sofast.framework.starter.common.dto.PageParamQuery;
import cc.sofast.framework.starter.common.dto.PageResult;
import cc.sofast.framework.starter.common.dto.SortableField;
import cn.zhxu.bs.SearchResult;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wxl
 */
public class PageUtil {

    /**
     * 将mybatis-plus分页结果转换为通用分页结果
     *
     * @param mpPage mybatis-plus分页结果
     * @return PageResult<T>
     */
    public static <T> PageResult<T> toPageResult(IPage<T> mpPage) {
        if (mpPage == null) {
            return new PageResult<>();
        }
        PageResult<T> pageResult = new PageResult<>();
        pageResult.setPageSize(mpPage.getSize());
        pageResult.setPageIndex(mpPage.getCurrent());
        pageResult.setTotalCount(mpPage.getTotal());
        pageResult.setData(mpPage.getRecords());
        return pageResult;
    }

    /**
     * 将mybatis-plus分页结果转换为通用分页结果
     *
     * @param search mybatis-plus分页结果
     * @return PageResult<T>
     */
    public static <T> PageResult<T> toPageResult(SearchResult<T> search, int pageNum) {
        if (search == null) {
            return new PageResult<>();
        }
        PageResult<T> pageResult = new PageResult<>();
        List<T> dataList = search.getDataList();
        pageResult.setPageSize((long) dataList.size());
        pageResult.setPageIndex((long) pageNum);
        pageResult.setTotalCount(search.getTotalCount().longValue());
        pageResult.setData(search.getDataList());
        return pageResult;
    }

    /**
     * 构建分页参数
     *
     * @param pageParam 分页参数
     * @return Page<T>
     */
    public static <T> Page<T> buildPage(PageParamQuery pageParam) {
        return buildPage(pageParam, null);
    }

    /**
     * 构建分页参数
     *
     * @param pageParam     分页参数
     * @param sortingFields 排序字段
     * @return Page<T>
     */
    public static <T> Page<T> buildPage(PageParamQuery pageParam, Collection<SortableField> sortingFields) {
        // 页码 + 数量
        Page<T> page = new Page<>(pageParam.getPageIndex(), pageParam.getPageSize());
        // 排序字段
        if (!CollectionUtils.isEmpty(sortingFields)) {
            page.addOrder(sortingFields.stream().map(sortingField -> SortableField.ORDER_ASC.equals(sortingField.getOrder())
                            ? OrderItem.asc(StringUtils.camelToUnderline(sortingField.getField()))
                            : OrderItem.desc(StringUtils.camelToUnderline(sortingField.getField())))
                    .collect(Collectors.toList()));
        }
        return page;
    }

}
