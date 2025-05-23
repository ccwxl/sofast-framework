package cc.sofast.framework.starter.security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.cors.CorsConfiguration;

/**
 * @author wxl
 */
@ConfigurationProperties(prefix = "sofast.web.cors")
public class SofastCorsConfiguration extends CorsConfiguration {
}
