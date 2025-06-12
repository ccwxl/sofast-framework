package cc.sofast.framework.starter.mybatis.mapper;

import cc.sofast.framework.starter.common.dto.PageParam;
import cc.sofast.framework.starter.common.dto.PageResult;
import cc.sofast.framework.starter.common.dto.SortableField;
import cc.sofast.framework.starter.common.dto.SortablePageParam;
import cc.sofast.framework.starter.mybatis.dataobject.BaseDO;
import cc.sofast.framework.starter.mybatis.utils.PageUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.github.yulichang.base.MPJBaseMapper;
import com.github.yulichang.interfaces.MPJBaseJoin;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author wxl
 */
public interface SofastBaseMapper<E extends BaseDO<E, P>, P extends Serializable> extends MPJBaseMapper<E> {

    /**
     * 根据id查询
     *
     * @param id id
     * @return T
     */
    default E selectColumnsById(Serializable id, SFunction<E, ?>... columns) {
        return selectOne(Wrappers.<E>lambdaQuery().select(columns).eq(E::getId, id));
    }

    /**
     * 分页查询
     *
     * @param pageParam    分页参数
     * @param queryWrapper 查询条件
     * @return PageResult<T>
     */
    default PageResult<E> selectPage(SortablePageParam pageParam, @Param("ew") Wrapper<E> queryWrapper) {
        return selectPage(pageParam, pageParam.getSortingFields(), queryWrapper);
    }

    /**
     * 分页查询
     *
     * @param pageParam    分页参数
     * @param queryWrapper 查询条件
     * @return PageResult<T>
     */
    default PageResult<E> selectPage(PageParam pageParam, @Param("ew") Wrapper<E> queryWrapper) {
        return selectPage(pageParam, null, queryWrapper);
    }

    /**
     * 分页查询
     *
     * @param pageParam     分页参数
     * @param sortingFields 排序字段
     * @param queryWrapper  查询条件
     * @return PageResult<T>
     */
    default PageResult<E> selectPage(PageParam pageParam, Collection<SortableField> sortingFields, @Param("ew") Wrapper<E> queryWrapper) {
        if (PageParam.PAGE_SIZE_NONE.equals(pageParam.getPageSize())) {
            List<E> list = selectList(queryWrapper);
            return PageResult.list(list);
        }
        IPage<E> mpPage = PageUtil.buildPage(pageParam, sortingFields);
        selectPage(mpPage, queryWrapper);
        return PageUtil.toPageResult(mpPage);
    }

    /**
     * 分页查询
     *
     * @param pageParam     分页参数
     * @param clazz         结果类型
     * @param lambdaWrapper 查询条件
     * @return PageResult<T>
     */
    default <DTO> PageResult<DTO> selectJoinPage(PageParam pageParam, Class<DTO> clazz, MPJLambdaWrapper<E> lambdaWrapper) {
        if (PageParam.PAGE_SIZE_NONE.equals(pageParam.getPageSize())) {
            List<DTO> list = selectJoinList(clazz, lambdaWrapper);
            return PageResult.list(list);
        }

        // MyBatis Plus Join 查询
        IPage<DTO> mpPage = PageUtil.buildPage(pageParam);
        mpPage = selectJoinPage(mpPage, clazz, lambdaWrapper);
        return PageUtil.toPageResult(mpPage);
    }

    /**
     * 分页查询
     *
     * @param pageParam        分页参数
     * @param resultTypeClass  结果类型
     * @param joinQueryWrapper 查询条件
     * @return PageResult<T>
     */
    default <DTO> PageResult<DTO> selectJoinPage(PageParam pageParam, Class<DTO> resultTypeClass, MPJBaseJoin<E> joinQueryWrapper) {
        IPage<DTO> mpPage = PageUtil.buildPage(pageParam);
        selectJoinPage(mpPage, resultTypeClass, joinQueryWrapper);
        return PageUtil.toPageResult(mpPage);
    }

    /**
     * 忽略逻辑删除查询
     *
     * @param queryWrapper queryWrapper
     * @return List<T>
     */
    List<E> selectIgnoreLogic(@Param(Constants.WRAPPER) Wrapper<E> queryWrapper);

    /**
     * 忽略逻辑删除
     *
     * @param queryWrapper queryWrapper
     * @return int
     */
    int deleteIgnoreLogic(@Param(Constants.WRAPPER) Wrapper<E> queryWrapper);

    /**
     * 总计数
     *
     * @return Long
     */
    default Long selectCount() {
        return selectCount(Wrappers.emptyWrapper());
    }

    /**
     * 总计数
     *
     * @param field field
     * @param value value
     * @return Long
     */
    default Long selectCount(String field, Object value) {
        return selectCount(new QueryWrapper<E>().eq(field, value));
    }

    /**
     * 查询所有
     *
     * @return List<T>
     */
    default List<E> selectList() {
        return selectList(Wrappers.emptyWrapper());
    }

    /**
     * 查询所有
     *
     * @param field field
     * @param value value
     * @return List<T>
     */
    default List<E> selectList(String field, Object value) {
        return selectList(new QueryWrapper<E>().eq(field, value));
    }

    /**
     * 查询所有
     *
     * @param field field
     * @param value value
     * @return List<T>
     */
    default List<E> selectList(SFunction<E, ?> field, Object value) {
        return selectList(new LambdaQueryWrapper<E>().eq(field, value));
    }

    /**
     * 查询所有
     *
     * @param field  field
     * @param values value
     * @return List<T>
     */
    default List<E> selectList(String field, Collection<?> values) {
        if (CollectionUtils.isEmpty(values)) {
            return Collections.emptyList();
        }
        return selectList(new QueryWrapper<E>().in(field, values));
    }

    /**
     * 查询所有
     *
     * @param field  field
     * @param values value
     * @return List<T>
     */
    default List<E> selectList(SFunction<E, ?> field, Collection<?> values) {
        if (CollectionUtils.isEmpty(values)) {
            return Collections.emptyList();
        }
        return selectList(new LambdaQueryWrapper<E>().in(field, values));
    }

    /**
     * 删除
     *
     * @param field field
     * @param value value
     * @return int
     */
    default int delete(String field, String value) {
        return delete(new QueryWrapper<E>().eq(field, value));
    }

    /**
     * 删除
     *
     * @param field field
     * @param value value
     * @return int
     */
    default int delete(SFunction<E, ?> field, Object value) {
        return delete(new LambdaQueryWrapper<E>().eq(field, value));
    }

    /**
     * 批量更新
     *
     * @param update 更新对象
     * @return int
     */
    default int updateBatch(E update) {
        return update(update, new QueryWrapper<>());
    }

    /**
     * 批量更新
     *
     * @param entities 实体们
     * @return int
     */
    default Boolean updateBatch(Collection<E> entities) {
        return Db.updateBatchById(entities);
    }

    /**
     * 批量更新
     *
     * @param entities 实体们
     * @param size     每批处理的数据量
     * @return int
     */
    default Boolean updateBatch(Collection<E> entities, int size) {
        return Db.updateBatchById(entities, size);
    }

    /**
     * 批量插入，适合大量数据插入
     *
     * @param entities 实体们
     */
    default Boolean insertBatch(Collection<E> entities) {
        return Db.saveBatch(entities);
    }
}
