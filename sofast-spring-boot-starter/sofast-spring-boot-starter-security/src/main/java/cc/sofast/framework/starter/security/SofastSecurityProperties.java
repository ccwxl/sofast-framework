package cc.sofast.framework.starter.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Collections;
import java.util.List;

/**
 * @author wxl
 */
@Data
@ConfigurationProperties(prefix = "sofast.security")
public class SofastSecurityProperties {
    /**
     * 一个在身份验证期间应该被忽略的URI列表。
     */
    private List<String> permitAllUrls = Collections.emptyList();

    /**
     * PasswordEncoder 加密复杂度，越高开销越大
     */
    private Integer passwordEncoderLength = 4;

}
