package cc.sofast.biz.component.log;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author wxl
 */
@AutoConfiguration
@MapperScan(basePackages = LogAutoConfiguration.MAPPER_BASE_PACKAGE)
@ComponentScan(value = LogAutoConfiguration.BASE_PACKAGE)
public class LogAutoConfiguration {
    public static final String BASE_PACKAGE = "cc.sofast.biz.component.log";
    public static final String MAPPER_BASE_PACKAGE = BASE_PACKAGE + ".mapper";


}
