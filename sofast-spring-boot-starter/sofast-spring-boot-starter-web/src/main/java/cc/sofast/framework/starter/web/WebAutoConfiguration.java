package cc.sofast.framework.starter.web;

import cc.sofast.framework.starter.web.exception.GlobalCommonException;
import cc.sofast.framework.starter.web.jackson.JacksonBuilderCustomizer;
import cc.sofast.framework.starter.web.listener.LaunchEventListener;
import cc.sofast.framework.starter.web.mvc.DefaultWebMvcConfigurer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.Map;

/**
 * @author wxl
 */
@AutoConfiguration
public class WebAutoConfiguration {

    @Bean
    public JacksonBuilderCustomizer jacksonBuilderCustomizer() {
        return new JacksonBuilderCustomizer();
    }

    @Bean
    public DefaultWebMvcConfigurer defaultWebMvcConfigurer(ObjectProvider<ObjectMapper> provider) {
        return new DefaultWebMvcConfigurer(provider.getIfAvailable(ObjectMapper::new));
    }

    @Bean
    public LaunchEventListener launchEventListener() {
        return new LaunchEventListener();
    }

    @Bean
    public GlobalCommonException globalCommonException() {

        return new GlobalCommonException();
    }
}
