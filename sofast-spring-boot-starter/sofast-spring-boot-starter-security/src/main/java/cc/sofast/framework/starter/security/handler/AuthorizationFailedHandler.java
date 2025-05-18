package cc.sofast.framework.starter.security.handler;

import cc.sofast.framework.starter.web.exception.GlobalCommonException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

/**
 * 授权失败处理器
 *
 * @author wxl
 */
public class AuthorizationFailedHandler implements AccessDeniedHandler {

    private final GlobalCommonException globalCommonException;

    public AuthorizationFailedHandler(GlobalCommonException globalCommonException) {
        this.globalCommonException = globalCommonException;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        globalCommonException.resolveException(request, response, accessDeniedException);
    }
}
