package cc.sofast.framework.starter.web;

import cc.sofast.framework.starter.web.jackson.JacksonBuilderCustomizer;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * @author wxl
 */
@AutoConfiguration
public class WebAutoConfiguration {

    @Bean
    public JacksonBuilderCustomizer jacksonBuilderCustomizer() {
        return new JacksonBuilderCustomizer();
    }
}
