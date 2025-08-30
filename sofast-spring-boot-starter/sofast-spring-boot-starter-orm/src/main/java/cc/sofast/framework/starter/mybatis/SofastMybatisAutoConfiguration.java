package cc.sofast.framework.starter.mybatis;

import cc.sofast.framework.starter.beansearch.MyBeanSearcher;
import cc.sofast.framework.starter.mybatis.method.CustomSqlInjector;
import cc.sofast.framework.starter.mybatis.objecthandler.MybatisPlusAutoFillColumnHandler;
import cc.sofast.framework.starter.beansearch.LogicDeleteParamFilter;
import cn.zhxu.bs.*;
import cn.zhxu.bs.boot.prop.BeanSearcherParams;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.function.Consumer;

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
    @ConditionalOnMissingBean(BeanSearcher.class)
    @ConditionalOnProperty(name = "bean-searcher.use-bean-searcher", havingValue = "true", matchIfMissing = true)
    public BeanSearcher beanSearcher(MetaResolver metaResolver,
                                     ParamResolver paramResolver,
                                     SqlResolver sqlResolver,
                                     SqlExecutor sqlExecutor,
                                     BeanReflector beanReflector,
                                     ObjectProvider<List<SqlInterceptor>> interceptors,
                                     ObjectProvider<List<ResultFilter>> processors,
                                     BeanSearcherParams config) {
        MyBeanSearcher searcher = new MyBeanSearcher();
        searcher.setMetaResolver(metaResolver);
        searcher.setParamResolver(paramResolver);
        searcher.setSqlResolver(sqlResolver);
        searcher.setSqlExecutor(sqlExecutor);
        searcher.setBeanReflector(beanReflector);
        searcher.setFailOnParamError(config.isFailOnError());
        ifAvailable(interceptors, searcher::setInterceptors);
        ifAvailable(processors, searcher::setResultFilters);
        return searcher;
    }

    static <T> void ifAvailable(ObjectProvider<T> provider, Consumer<T> consumer) {
        // 为了兼容 1.x 的 SpringBoot，最低兼容到 v1.4
        // 不直接使用 ObjectProvider.ifAvailable 方法
        T dependency = provider.getIfAvailable();
        if (dependency != null) {
            consumer.accept(dependency);
        }
    }
}
