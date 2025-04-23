package cc.sofast.biz.component.rbac;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author wxl
 */
@AutoConfiguration
@MapperScan(RbacAutoConfiguration.MAPPER_BASE_PACKAGE)
@ComponentScan(RbacAutoConfiguration.BASE_PACKAGE)
@EnableConfigurationProperties(RbacProperties.class)
public class RbacAutoConfiguration {
    public static final String BASE_PACKAGE = "cc.sofast.biz.component.rbac";
    public static final String MAPPER_BASE_PACKAGE = BASE_PACKAGE + ".mapper";

}
