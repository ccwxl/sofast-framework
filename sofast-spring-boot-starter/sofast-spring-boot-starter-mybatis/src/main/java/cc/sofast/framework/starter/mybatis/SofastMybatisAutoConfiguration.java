package cc.sofast.framework.starter.mybatis;

import cc.sofast.framework.starter.mybatis.beansearch.MyDefaultParamResolver;
import cc.sofast.framework.starter.mybatis.method.CustomSqlInjector;
import cc.sofast.framework.starter.mybatis.objecthandler.MybatisPlusAutoFillColumnHandler;
import cc.sofast.framework.starter.mybatis.beansearch.LogicDeleteParamFilter;
import cn.zhxu.bs.*;
import cn.zhxu.bs.boot.prop.BeanSearcherParams;
import cn.zhxu.bs.group.GroupResolver;
import cn.zhxu.bs.implement.DefaultParamResolver;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

import java.util.List;

/**
 * @author wxl
 */
@AutoConfiguration
public class SofastMybatisAutoConfiguration {

    @Bean
    public CustomSqlInjector mySqlInjector() {
        return new CustomSqlInjector();
    }

    @Bean
    public MybatisPlusAutoFillColumnHandler autoFillColumnHandler() {

        return new MybatisPlusAutoFillColumnHandler();
    }

    @Bean
    public LogicDeleteParamFilter logicDeleteParamFilter() {

        return new LogicDeleteParamFilter();
    }

    @Bean
    @ConditionalOnMissingBean(ParamResolver.class)
    public ParamResolver paramResolver(PageExtractor pageExtractor,
                                       FieldOpPool fieldOpPool,
                                       List<ParamFilter> paramFilters,
                                       List<FieldConvertor.ParamConvertor> convertors,
                                       GroupResolver groupResolver,
                                       BeanSearcherParams config) {
        MyDefaultParamResolver paramResolver = new MyDefaultParamResolver(convertors, paramFilters);
        paramResolver.setPageExtractor(pageExtractor);
        paramResolver.setFieldOpPool(fieldOpPool);
        paramResolver.setGroupResolver(groupResolver);
        BeanSearcherParams.Group group = config.getGroup();
        paramResolver.getConfiguration()
                .gexprMerge(group.isMergeable())
                .groupSeparator(group.getSeparator())
                .gexpr(group.getExprName())
                .selectExclude(config.getSelectExclude())
                .onlySelect(config.getOnlySelect())
                .separator(config.getSeparator())
                .op(config.getOperatorKey())
                .ic(config.getIgnoreCaseKey())
                .orderBy(config.getOrderBy())
                .order(config.getOrder())
                .sort(config.getSort());
        return paramResolver;
    }
}
