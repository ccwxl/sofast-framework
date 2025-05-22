package cc.sofast.framework.starter.redis.cache;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author wxl
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties("sofast.cache")
public class SofastCacheProperties {

    private int redisScanBatchSize=100;

}
