package cc.sofast.framework.starter.mybatis.beansearch;

import cn.zhxu.bs.*;
import cn.zhxu.bs.bean.Cluster;
import cn.zhxu.bs.bean.DbType;
import cn.zhxu.bs.implement.DefaultParamResolver;
import cn.zhxu.bs.param.FieldParam;
import cn.zhxu.bs.util.MapBuilder;
import cn.zhxu.bs.util.MapWrapper;
import cn.zhxu.bs.util.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @author wxl
 */
@Slf4j
public class MyDefaultParamResolver extends DefaultParamResolver {

    public MyDefaultParamResolver(List<FieldConvertor.ParamConvertor> convertors, List<ParamFilter> paramFilters) {
        super(convertors, paramFilters);
    }

    @Override
    protected List<FieldParam> extractFieldParams(Collection<FieldMeta> fieldMetas2, MapWrapper paraMap) {
        Collection<FieldMeta> fieldMetas = new ArrayList<>(fieldMetas2);
        Set<String> filedSet = getAllParamFiled(paraMap);
        BeanMeta<?> beanMeta = getBeanMeta(fieldMetas);
        if (!CollectionUtils.isEmpty(filedSet)) {
            for (String key : filedSet) {
                FieldMeta fieldMeta = new FieldMeta(beanMeta, key, null, null, UUID.randomUUID().toString(),
                        true, new Class[]{}, DbType.UNKNOWN, Cluster.AUTO);
                fieldMetas.add(fieldMeta);
            }
        }
        Map<String, Set<Integer>> fieldIndicesMap = new HashMap<>();
        for (String key : paraMap.keySet()) {
            int index = key.lastIndexOf(getConfiguration().separator());
            if (index > 0 && key.length() > index + 1) {
                String suffix = key.substring(index + 1);
                if (INDEX_PATTERN.matcher(suffix).matches()) {
                    String field = key.substring(0, index);
                    mapFieldIndex(fieldIndicesMap, field, Integer.parseInt(suffix));
                }
            }
            mapFieldIndex(fieldIndicesMap, key, 0);
        }
        List<FieldParam> fieldParams = new ArrayList<>();
        for (FieldMeta meta : fieldMetas) {
            if (!meta.isConditional()) {
                continue;
            }
            Set<Integer> indices = fieldIndicesMap.get(meta.getName());
            FieldParam param = toFieldParam(meta, indices, paraMap);
            if (param != null) {
                fieldParams.add(param);
            }
        }
        return fieldParams;
    }

    private BeanMeta<?> getBeanMeta(Collection<FieldMeta> fieldMetas) {
        for (FieldMeta meta : fieldMetas) {
            if (meta.getBeanMeta() != null) {
                return meta.getBeanMeta();
            }
        }
        return null;
    }

    private Set<String> getAllParamFiled(MapWrapper paraMap) {
        HashSet<String> hashSet = new HashSet<>();
        Set<String> strings = paraMap.keySet();
        for (String keys : strings) {
            String fieldParam = MapBuilder.FIELD_PARAM;
            if (keys.startsWith(fieldParam)) {
                hashSet.add(keys.substring(fieldParam.length()));
                log.debug("add keys:{}", keys);
            }
        }
        return hashSet;
    }

    @Override
    protected FieldParam toFieldParam(FieldMeta meta, Set<Integer> indices, MapWrapper paraMap) {
        String field = meta.getName();
        FieldParam param = getFieldParam(paraMap, field);
        FieldOp operator = allowedOperator(toOperator(field, paraMap, param), meta.getOnlyOn());
        if (operator == null) {
            // 表示该字段不支持 op 的检索
            return null;
        }
        if (operator.lonely()) {
            if (param == null) {
                return new FieldParam(field, operator);
            }
            return new FieldParam(field, operator, param.getValueList(), param.isIgnoreCase());
        }
        if ((indices == null || indices.isEmpty()) && param == null) {
            return null;
        }
        List<FieldParam.Value> values = param != null ? param.getValueList() : new ArrayList<>();
        if (values.isEmpty() && indices != null) {
            for (int index : indices) {
                Object value = paraMap.get1(field + getConfiguration().separator() + index);
                if (index == 0 && value == null) {
                    value = paraMap.get1(field);
                }
                value = convertParamValue(meta, value);
                values.add(new FieldParam.Value(value, index));
            }
        } else if (!values.isEmpty()) {
            for (int index = 0; index < values.size(); index++) {
                FieldParam.Value value = values.get(index);
                if (value != null) {
                    Object v = convertParamValue(meta, value.getValue());
                    values.set(index, new FieldParam.Value(v, index));
                }
            }
        }
        if (isAllEmpty(values)) {
            return null;
        }
        Boolean ignoreCase = null;
        if (param != null) {
            ignoreCase = param.isIgnoreCase();
        }
        if (ignoreCase == null) {
            ignoreCase = ObjectUtils.toBoolean(paraMap.get1(field + getConfiguration().icSuffix()));
        }
        return new FieldParam(field, operator, values, ignoreCase);
    }
}
