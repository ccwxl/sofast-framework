package cc.sofast.framework.starter.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author wxl
 */
@Data
@ConfigurationProperties(prefix = "sofast.security")
public class SofastSecurityProperties {
}
