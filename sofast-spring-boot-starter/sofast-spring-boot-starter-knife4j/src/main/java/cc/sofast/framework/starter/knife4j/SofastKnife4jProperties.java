package cc.sofast.framework.starter.knife4j;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.info.License;
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
        /**
         * 协议
         */
        @NestedConfigurationProperty
        private License license ;
        /**
         * 扩展文档
         */
        @NestedConfigurationProperty
        private ExternalDocumentation externalDocumentation;
    }
}
