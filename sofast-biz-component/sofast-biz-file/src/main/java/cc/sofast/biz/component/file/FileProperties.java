package cc.sofast.biz.component.file;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author wxl
 */
@Data
@ConfigurationProperties(prefix = "sofast.file")
public class FileProperties {

    public Api api = new Api();

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Api {
        private String basePath;
        private boolean enabled = false;
    }
}
