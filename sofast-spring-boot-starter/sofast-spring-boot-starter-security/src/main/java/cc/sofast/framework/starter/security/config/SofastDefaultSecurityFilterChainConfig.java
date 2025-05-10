package cc.sofast.framework.starter.security.config;

import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

/**
 * @author wxl
 */
@AutoConfigureOrder(-1)
@EnableMethodSecurity(securedEnabled = true)
public class SofastDefaultSecurityFilterChainConfig {


}
