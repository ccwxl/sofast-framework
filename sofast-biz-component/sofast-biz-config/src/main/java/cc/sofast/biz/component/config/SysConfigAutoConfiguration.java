package cc.sofast.biz.component.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author wxl
 */
@AutoConfiguration
@EnableConfigurationProperties(SysConfigProperties.class)
public class SysConfigAutoConfiguration {
}
