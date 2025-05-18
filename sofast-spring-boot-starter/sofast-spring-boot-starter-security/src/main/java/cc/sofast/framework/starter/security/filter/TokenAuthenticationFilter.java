package cc.sofast.framework.starter.security.filter;

import cc.sofast.framework.starter.security.context.LoginUser;
import cc.sofast.framework.starter.security.support.DynamicPermitAllRequestMatcher;
import cc.sofast.framework.starter.security.utils.SecurityUtils;
import cc.sofast.framework.starter.web.exception.GlobalCommonException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

/**
 * @author wxl
 */
@Slf4j
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final GlobalCommonException globalCommonException;

    private final DynamicPermitAllRequestMatcher dynamicPermitAllRequestMatcher;

    public TokenAuthenticationFilter(
            GlobalCommonException globalCommonException,
            DynamicPermitAllRequestMatcher dynamicPermitAllRequestMatcher) {
        this.globalCommonException = globalCommonException;
        this.dynamicPermitAllRequestMatcher = dynamicPermitAllRequestMatcher;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        boolean matches = dynamicPermitAllRequestMatcher.matches(request);
        if (matches) {
            request.setAttribute(DynamicPermitAllRequestMatcher.PERMIT_ALL, true);
            filterChain.doFilter(request, response);
            return;
        }
        try {
            String token = SecurityUtils.getBearerToken(request);
            if (StringUtils.hasLength(token)) {
                LoginUser loginUser = authentication(token);
                if (loginUser != null) {
                    SecurityUtils.setLoginUser(loginUser, request);
                }
            }
        } catch (Exception e) {
            log.error("TokenAuthentication failed.", e);
            globalCommonException.resolveException(request, response, e);
        }
        filterChain.doFilter(request, response);
    }

    private LoginUser authentication(String token) {


        return null;
    }
}
