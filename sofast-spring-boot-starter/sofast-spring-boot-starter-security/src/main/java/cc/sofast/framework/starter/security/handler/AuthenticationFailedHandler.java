package cc.sofast.framework.starter.security.handler;

import cc.sofast.framework.starter.web.exception.GlobalCommonException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

/**
 * 访问一个需要认证的 URL 资源，但是此时自己尚未认证（登录）的情况下触发
 *
 * @author wxl
 */
public class AuthenticationFailedHandler implements AuthenticationEntryPoint {

    private final GlobalCommonException globalCommonException;

    public AuthenticationFailedHandler(GlobalCommonException globalCommonException) {
        this.globalCommonException = globalCommonException;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        globalCommonException.resolveException(request, response, authException);
    }
}
