package cc.sofast.biz.component.rbac;

import cc.sofast.biz.component.common.ComponentWebInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * @author wxl
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = RbacProperties.PREFIX)
public class RbacProperties {

    public static final String PREFIX = "sofast.rbac";
    public static final String WEB_PREFIX = PREFIX + ".web";

    @NestedConfigurationProperty
    private ComponentWebInfo web;
}
