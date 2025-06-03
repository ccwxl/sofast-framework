package cc.sofast.framework.starter.mybatis.searchbean;

import cn.zhxu.bs.BeanMeta;
import cn.zhxu.bs.ParamFilter;

import java.util.Map;

/**
 * 逻辑删除参数增加
 *
 * @author wxl
 */
public class LogicDeleteParamFilter implements ParamFilter {
    @Override
    public <T> Map<String, Object> doFilter(BeanMeta<T> beanMeta, Map<String, Object> paraMap) {
        // 为逻辑删除字段，指定参数值
        paraMap.put("deleted", "0");
        return paraMap;
    }
}
