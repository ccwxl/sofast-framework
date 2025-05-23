package cc.sofast.framework.starter.security.config;

import cc.sofast.framework.starter.security.filter.TokenAuthenticationFilter;
import cc.sofast.framework.starter.security.handler.AuthenticationFailedHandler;
import cc.sofast.framework.starter.security.support.DynamicPermitAllRequestMatcher;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

import java.util.List;

/**
 * @author wxl
 */
@AutoConfigureOrder(-1)
@EnableMethodSecurity(securedEnabled = true)
public class SofastDefaultSecurityFilterChainConfig {

    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    @Order(1)
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http, TokenAuthenticationFilter tokenFilter,
                                                          AuthenticationFailedHandler authenticationFailedHandler,
                                                          DynamicPermitAllRequestMatcher permitAllRequestMatcher,
                                                          AccessDeniedHandler accessDeniedHandler, SofastCorsConfiguration corsConfiguration,
                                                          List<AuthorizeRequestsCustomizer> authorizeRequestsCustomizers) throws Exception {
        http.cors(c -> c.configurationSource(_ -> corsConfiguration))
                .csrf(AbstractHttpConfigurer::disable)
                .anonymous(AbstractHttpConfigurer::disable)
                .sessionManagement(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .headers(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .requestCache(AbstractHttpConfigurer::disable)
                .exceptionHandling(c -> c.authenticationEntryPoint(authenticationFailedHandler)
                        .accessDeniedHandler(accessDeniedHandler))
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        .requestMatchers(permitAllRequestMatcher).permitAll()
                        .anyRequest().authenticated()
                )
                .authorizeHttpRequests(c -> authorizeRequestsCustomizers.forEach(customizer -> customizer.customize(c)));
        http.addFilterBefore(tokenFilter, AuthorizationFilter.class);
        return http.build();
    }
}
