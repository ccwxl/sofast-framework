package cc.sofast.framework.starter.mybatis;

import cc.sofast.framework.starter.mybatis.method.CustomSqlInjector;
import cc.sofast.framework.starter.mybatis.objecthandler.MybatisPlusAutoFillColumnHandler;
import cc.sofast.framework.starter.mybatis.beansearch.LogicDeleteParamFilter;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

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
}
