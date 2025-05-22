package cc.sofast.framework.starter.security.utils;

import cc.sofast.framework.starter.security.context.LoginUser;
import cn.hutool.core.util.StrUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;

import java.util.Collections;

/**
 * @author wxl
 */
public class SecurityUtils {
    private static final String REQUEST_ATTRIBUTE_LOGIN_USER_ID = "login_user_id";
    public static final String AUTHORIZATION_BEARER = "Bearer";

    public static String getBearerToken(HttpServletRequest request) {
        // 1. 获得 Token。优先级：Header > Parameter
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StrUtil.isEmpty(token)) {
            token = request.getParameter(HttpHeaders.AUTHORIZATION);
        }
        if (!StringUtils.hasText(token)) {
            return null;
        }
        // 2. 去除 Token 中带的 Bearer
        int index = token.indexOf(AUTHORIZATION_BEARER + " ");
        return index >= 0 ? token.substring(index + 7).trim() : token;
    }

    public static void setLoginUser(LoginUser loginUser, HttpServletRequest request) {
        Authentication authentication = buildAuthentication(loginUser, request);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        request.setAttribute(REQUEST_ATTRIBUTE_LOGIN_USER_ID, loginUser.getId());
    }

    private static Authentication buildAuthentication(LoginUser loginUser, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginUser, null, Collections.emptyList());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return authenticationToken;
    }

    public static Long getUserId() {
        LoginUser loginUser = getLoginUser();
        return loginUser != null ? loginUser.getId() : null;
    }

    public static Authentication getAuthentication() {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context == null) {
            return null;
        }
        return context.getAuthentication();
    }

    public static LoginUser getLoginUser() {
        Authentication authentication = getAuthentication();
        if (authentication == null) {
            return null;
        }
        return authentication.getPrincipal() instanceof LoginUser ? (LoginUser) authentication.getPrincipal() : null;
    }
}
