package cc.sofast.framework.starter.mybatis.beansearch;

import cn.zhxu.bs.*;
import cn.zhxu.bs.bean.Cluster;
import cn.zhxu.bs.bean.DbType;
import cn.zhxu.bs.implement.DefaultBeanSearcher;
import cn.zhxu.bs.param.FetchType;
import cn.zhxu.bs.param.FieldParam;
import cn.zhxu.bs.param.OrderBy;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

/**
 * @author wxl
 */
public class MyBeanSearcher extends DefaultBeanSearcher {

    @Override
    protected <T> SqlResult<T> doSearch(Class<T> beanClass, Map<String, Object> paraMap, FetchType fetchType) {
        if (getSqlExecutor() == null) {
            throw new SearchException("You must set a sqlExecutor before searching.");
        }
        BeanMeta<T> beanMeta = getMetaResolver().resolve(beanClass);
        // 逻辑删除字段条件过滤
        if (beanMeta.getFieldMeta("deleted") == null) {
            String deleted = StringUtils.camelToUnderline("deleted");
            FieldMeta fieldMeta = new FieldMeta(beanMeta, "deleted", null, new SqlSnippet(deleted), UUID.randomUUID().toString(),
                    true, new Class[]{}, DbType.UNKNOWN, Cluster.FALSE);
            beanMeta.addFieldMeta(fieldMeta);
        }

        // 字段条件过滤 添加到BeanMeta
        for (Map.Entry<String, Object> searchParam : paraMap.entrySet()) {
            Object value = searchParam.getValue();
            String key = searchParam.getKey();
            //where 条件
            if (value instanceof FieldParam fieldParam) {
                FieldMeta fieldMeta = new FieldMeta(beanMeta, fieldParam.getName(), null, new SqlSnippet(StringUtils.camelToUnderline(fieldParam.getName())), UUID.randomUUID().toString(),
                        true, new Class[]{}, DbType.UNKNOWN, Cluster.FALSE);
                if (beanMeta.getFieldMeta(fieldParam.getName()) == null) {
                    beanMeta.addFieldMeta(fieldMeta);
                }
            }
            // 排序条件
            if ("cn.zhxu.bs.param.OrderBy".equals(key)) {
                if (value instanceof ArrayList<?> arrayList) {
                    for (Object o : arrayList) {
                        if (o instanceof OrderBy orderBy) {
                            FieldMeta fieldMeta = beanMeta.getFieldMeta(orderBy.getSort());
                            if (fieldMeta == null) {
                                fieldMeta = new FieldMeta(beanMeta, orderBy.getSort(), null, new SqlSnippet(StringUtils.camelToUnderline(orderBy.getSort())), UUID.randomUUID().toString(),
                                        true, new Class[]{}, DbType.UNKNOWN, Cluster.FALSE);
                                beanMeta.addFieldMeta(fieldMeta);
                            }
                        }
                    }
                }
            }
        }
        SearchParam searchParam;
        try {
            searchParam = getParamResolver().resolve(beanMeta, fetchType, paraMap);
        } catch (IllegalParamException e) {
            if (isFailOnParamError()) {
                throw e;
            }
            log.warn("Empty data will be returned, because of illegal params detected: [{}]", e.getMessage());
            return emptyResult(beanMeta, fetchType);
        }
        SearchSql<T> searchSql = getSqlResolver().resolve(beanMeta, searchParam);
        SqlResult<T> sqlResult = getSqlExecutor().execute(intercept(searchSql, paraMap, fetchType));
        sqlResult.setPageSize(searchParam.getPageSize());
        return sqlResult;
    }

    private <T> SqlResult<T> emptyResult(BeanMeta<T> beanMeta, FetchType fetchType) {
        SearchSql<T> searchSql = new SearchSql<>(beanMeta, null);
        for (String summaryField : fetchType.getSummaryFields()) {
            searchSql.addSummaryAlias(summaryField);
        }
        return new SqlResult<>(searchSql, SqlResult.ResultSet.EMPTY, SqlResult.ResultSet.EMPTY);
    }
}
