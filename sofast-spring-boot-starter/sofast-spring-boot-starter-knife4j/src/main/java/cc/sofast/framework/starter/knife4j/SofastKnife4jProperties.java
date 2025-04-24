package cc.sofast.framework.starter.knife4j;

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
@ConfigurationProperties(prefix = "sofast")
public class SofastKnife4jProperties {

    private Knife4j knife4j = new Knife4j();

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Knife4j {

        /**
         * Knife4j UI 上显示的标题
         */
        private String title = "";

        /**
         * Knife4j UI 上显示的简介
         */
        private String description = "";

        /**
         * Knife4j UI 上显示的版本号
         */
        private String version = "";

    }
}
