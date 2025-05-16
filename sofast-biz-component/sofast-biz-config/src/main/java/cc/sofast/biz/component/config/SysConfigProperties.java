package cc.sofast.biz.component.config;

import cc.sofast.biz.component.common.ComponentWebInfo;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * @author wxl
 */
@Data
@ConfigurationProperties(prefix = "sofast.sysconfig")
public class SysConfigProperties {

    @NestedConfigurationProperty
    private ComponentWebInfo web;

}
