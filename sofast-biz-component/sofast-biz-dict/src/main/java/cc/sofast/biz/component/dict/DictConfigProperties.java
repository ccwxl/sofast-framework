package cc.sofast.biz.component.dict;

import cc.sofast.biz.component.common.ComponentWebInfo;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * @author wxl
 */
@Data
@ConfigurationProperties(prefix = "sofast.dict")
public class DictConfigProperties {

    @NestedConfigurationProperty
    private ComponentWebInfo web;

}
