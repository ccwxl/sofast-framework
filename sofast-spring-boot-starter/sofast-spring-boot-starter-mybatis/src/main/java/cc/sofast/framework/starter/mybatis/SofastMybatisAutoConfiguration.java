package cc.sofast.framework.starter.mybatis;

import cc.sofast.framework.starter.mybatis.method.CustomSqlInjector;
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
}
