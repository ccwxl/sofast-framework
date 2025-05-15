package cc.sofast.biz.component.file;

import cn.hutool.core.collection.CollectionUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.MediaType;

import java.util.Locale;
import java.util.Set;

/**
 * @author wxl
 */
@Data
@ConfigurationProperties(prefix = "sofast.file")
public class FileProperties {

    public Api api = new Api();

    private Set<String> allowFiles;

    private Set<String> denyFiles;

    private Set<String> allowMediaType;

    private Set<String> denyMediaType;

    public boolean denied(String name) {
        String suffix = (name.contains(".") ? name.substring(name.lastIndexOf(".") + 1) : "").toLowerCase(Locale.ROOT);
        boolean defaultDeny = false;
        if (CollectionUtil.isNotEmpty(denyFiles)) {
            if (denyFiles.contains(suffix)) {
                return true;
            }
        }

        if (CollectionUtil.isNotEmpty(allowFiles)) {
            if (allowFiles.contains(suffix)) {
                return false;
            }
            defaultDeny = true;
        }
        return defaultDeny;
    }

    public boolean denied(String name, MediaType mediaType) {
        boolean defaultDeny = denied(name);
        if (CollectionUtil.isNotEmpty(denyMediaType)) {
            if (denyMediaType.contains(mediaType.toString())) {
                return true;
            }
            defaultDeny = false;
        }

        if (CollectionUtil.isNotEmpty(allowMediaType)) {
            if (allowMediaType.contains(mediaType.toString())) {
                return false;
            }
            defaultDeny = true;
        }

        return defaultDeny;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Api {
        private String basePath;
        private boolean enabled = false;
    }
}
