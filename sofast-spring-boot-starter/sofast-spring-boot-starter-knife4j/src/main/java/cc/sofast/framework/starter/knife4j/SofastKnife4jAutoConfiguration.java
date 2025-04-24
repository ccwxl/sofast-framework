package cc.sofast.framework.starter.knife4j;

import cc.sofast.framework.starter.knife4j.customizer.BaseEnumCustomizer;
import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.configuration.SpringDocConfiguration;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.http.HttpHeaders;

/**
 * @author wxl
 */
@EnableKnife4j
@Import(value = {BaseEnumCustomizer.class})
@ConditionalOnExpression(value = "!${knife4j.production:false}")
@AutoConfiguration(before = SpringDocConfiguration.class)
@EnableConfigurationProperties(SofastKnife4jProperties.class)
public class SofastKnife4jAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(value = OpenAPI.class)
    public OpenAPI openApi(SofastKnife4jProperties knife4jProperties, ConfigurableEnvironment env) {
        OpenAPI openApi = new OpenAPI();
        // 基本信息
        openApi.setInfo(buildInfo(knife4jProperties));

        // 全局安全要求
        SecurityScheme securityScheme = new SecurityScheme()
                // 取个名字，方便被引用
                .name(HttpHeaders.AUTHORIZATION)
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer ")
                .in(SecurityScheme.In.HEADER);

        SecurityRequirement securityRequirement = new SecurityRequirement()
                // 引用上面定义的SecurityScheme
                .addList(HttpHeaders.AUTHORIZATION);

        openApi
                .components(new Components()
                        // 在components里定义SecurityScheme
                        .addSecuritySchemes(HttpHeaders.AUTHORIZATION, securityScheme))
                // 添加SecurityRequirement作为全局安全要求
                .addSecurityItem(securityRequirement);

        return openApi;
    }

    private Info buildInfo(SofastKnife4jProperties knife4jProperties) {
        Info info = new Info();
        SofastKnife4jProperties.Knife4j knife4j = knife4jProperties.getKnife4j();
        info.setTitle(knife4j.getTitle());
        info.setDescription(knife4j.getDescription());
        info.setVersion(knife4j.getVersion());
        return info;
    }
}
