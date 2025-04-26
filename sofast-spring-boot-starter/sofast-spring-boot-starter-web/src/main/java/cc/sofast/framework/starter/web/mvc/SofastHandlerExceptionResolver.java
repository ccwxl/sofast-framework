package cc.sofast.framework.starter.web.mvc;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

/**
 * @author wxl
 */
@Slf4j
public class SofastHandlerExceptionResolver extends DefaultHandlerExceptionResolver {
    @Override
    protected void logException(Exception ex, HttpServletRequest request) {
        log.error("HTTP request: [{}] Exception: ", request.getRequestURL(), ex);
    }
}
