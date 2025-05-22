package cc.sofast.framework.starter.security;

import cc.sofast.framework.starter.security.context.TransmittableThreadLocalSecurityContextHolderStrategy;
import cc.sofast.framework.starter.security.filter.TokenAuthenticationFilter;
import cc.sofast.framework.starter.security.handler.AuthenticationFailedHandler;
import cc.sofast.framework.starter.security.handler.AuthorizationFailedHandler;
import cc.sofast.framework.starter.security.handler.SofastSecurityExceptionHandler;
import cc.sofast.framework.starter.security.support.DynamicPermitAllRequestMatcher;
import cc.sofast.framework.starter.security.token.TokenService;
import cc.sofast.framework.starter.security.token.impl.RedisTokenService;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * @author wxl
 */
@AutoConfiguration
@EnableConfigurationProperties(SofastSecurityProperties.class)
public class SecurityAutoConfiguration {

    @Bean
    public SofastSecurityExceptionHandler securityExceptionHandler() {

        return new SofastSecurityExceptionHandler();
    }

    @Bean
    public AuthenticationFailedHandler authenticationFailedHandler(SofastSecurityExceptionHandler securityExceptionHandler) {

        return new AuthenticationFailedHandler(securityExceptionHandler);
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler(SofastSecurityExceptionHandler securityExceptionHandler) {

        return new AuthorizationFailedHandler(securityExceptionHandler);
    }

    @Bean
    public TokenAuthenticationFilter jwtTokenFilter(DynamicPermitAllRequestMatcher requestMatcher,
                                                    SofastSecurityExceptionHandler securityExceptionHandler,
                                                    TokenService tokenService) {

        return new TokenAuthenticationFilter(securityExceptionHandler, requestMatcher, tokenService);
    }

    @Bean
    public TokenService tokenService(SofastSecurityProperties securityProperties) {

        return new RedisTokenService(securityProperties);
    }

    @Bean
    public DynamicPermitAllRequestMatcher permitAllRequestMatcher(
            SofastSecurityProperties securityProperties, RequestMappingHandlerMapping requestMapping) {

        return new DynamicPermitAllRequestMatcher(securityProperties, requestMapping);
    }

    /**
     * 声明调用 {@link SecurityContextHolder#setStrategyName(String)} 方法，
     * 设置使用 {@link TransmittableThreadLocalSecurityContextHolderStrategy} 作为 Security 的上下文策略
     */
    @Bean
    public MethodInvokingFactoryBean securityContextHolderMethodInvokingFactoryBean() {
        MethodInvokingFactoryBean methodInvokingFactoryBean = new MethodInvokingFactoryBean();
        methodInvokingFactoryBean.setTargetClass(SecurityContextHolder.class);
        methodInvokingFactoryBean.setTargetMethod("setStrategyName");
        methodInvokingFactoryBean.setArguments(TransmittableThreadLocalSecurityContextHolderStrategy.class.getName());
        return methodInvokingFactoryBean;
    }

    /**
     * Spring Security 加密器
     **/
    @Bean
    public PasswordEncoder passwordEncoder(SofastSecurityProperties securityProperties) {
        return new BCryptPasswordEncoder(securityProperties.getPasswordEncoderLength());
    }
}
