package cc.sofast.framework.starter.security.filter;

import cc.sofast.framework.starter.security.context.LoginUser;
import cc.sofast.framework.starter.security.handler.SofastSecurityExceptionHandler;
import cc.sofast.framework.starter.security.support.DynamicPermitAllRequestMatcher;
import cc.sofast.framework.starter.security.token.SecurityUserInfo;
import cc.sofast.framework.starter.security.token.TokenInfo;
import cc.sofast.framework.starter.security.token.TokenService;
import cc.sofast.framework.starter.security.utils.RedisUserUtils;
import cc.sofast.framework.starter.security.utils.SecurityUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * @author wxl
 */
@Slf4j
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final SofastSecurityExceptionHandler securityExceptionHandler;

    private final DynamicPermitAllRequestMatcher dynamicPermitAllRequestMatcher;

    private final TokenService tokenService;

    public TokenAuthenticationFilter(
            SofastSecurityExceptionHandler securityExceptionHandler,
            DynamicPermitAllRequestMatcher dynamicPermitAllRequestMatcher, TokenService tokenService) {
        this.securityExceptionHandler = securityExceptionHandler;
        this.dynamicPermitAllRequestMatcher = dynamicPermitAllRequestMatcher;
        this.tokenService = tokenService;
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
            securityExceptionHandler.resolveException(request, response, e);
        }
        filterChain.doFilter(request, response);
    }

    private LoginUser authentication(String token) {
        TokenInfo tokenInfo = tokenService.loadByToken(token);
        if (tokenInfo == null) {
            return null;
        }
        Long uid = tokenInfo.getUid();
        SecurityUserInfo securityUserInfo = RedisUserUtils.getLoginUser(uid);
        LoginUser loginUser = new LoginUser();
        loginUser.setRoles(securityUserInfo.getRoles());
        loginUser.setPermissions(securityUserInfo.getPermissions());
        loginUser.setOrgIds(securityUserInfo.getOrgIds());
        loginUser.setInfo(securityUserInfo.getInfo());
        loginUser.setAccessToken(token);
        loginUser.setTokenExpireTime(tokenInfo.getExpireTime());
        return loginUser;
    }
}
