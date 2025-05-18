package cc.sofast.framework.starter.security.support;

import cc.sofast.framework.starter.security.SofastSecurityProperties;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PathPatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.*;

/**
 * @author wxl
 */
public class DynamicPermitAllRequestMatcher implements RequestMatcher {

    /**
     * 一个常量，表示在不需要认证的情况下，用来标记 HTTP 请求的属性名称。
     */
    public static final String PERMIT_ALL = "PERMIT_ALL";

    private final Set<String> staticListURL = new HashSet<>();

    /**
     * 一个 AntPathMatcher 实例，用来匹配 HTTP 请求路径和 ignoreUriList 中的 URI。
     */
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    /**
     * 一个 AppConsoleProperties 实例，包含了在不需要认证的情况下需要被忽略的 URI 列表。
     */
    private final SofastSecurityProperties securityProperties;

    public DynamicPermitAllRequestMatcher(SofastSecurityProperties securityProperties,
                                          RequestMappingHandlerMapping requestMappingHandlerMapping) {
        this.securityProperties = securityProperties;
        processStaticUrl(requestMappingHandlerMapping);
    }

    private void processStaticUrl(RequestMappingHandlerMapping requestMappingHandlerMapping) {
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();
        handlerMethods.forEach((info, method) -> {
            if (Objects.nonNull(method.getMethodAnnotation(IgnoreAuth.class))) {
                PathPatternsRequestCondition pathPatternsCondition = info.getPathPatternsCondition();
                if (pathPatternsCondition != null) {
                    Set<String> patternValues = pathPatternsCondition.getPatternValues();
                    staticListURL.addAll(patternValues);
                }
            }
        });
    }

    /**
     * 确定是否需要对给定的 HTTP 请求进行认证。
     *
     * @param request 传入的 HTTP 请求。
     * @return 如果不需要对 HTTP 请求进行认证，返回 true，否则返回 false。
     */
    @Override
    public boolean matches(HttpServletRequest request) {
        Object attribute = request.getAttribute(PERMIT_ALL);
        if (attribute != null && attribute.equals(Boolean.TRUE)) {
            // 如果 HTTP 请求的属性已经被设置为放行，立即返回 true。
            return true;
        }
        String requestPath = getRequestPath(request);
        //fist static
        for (String uri : staticListURL) {
            if (antPathMatcher.match(uri, requestPath)) {
                // 如果 HTTP 请求路径与 ignoreUriList 中的任何 URI 匹配，将 HTTP 请求的属性设置为放行，并返回 true。
                request.setAttribute(PERMIT_ALL, Boolean.TRUE);
                return true;
            }
        }

        List<String> ignoreUriList = securityProperties.getPermitAllUrls();
        // second dynamic
        for (String uri : ignoreUriList) {
            if (antPathMatcher.match(uri, requestPath)) {
                // 如果 HTTP 请求路径与 ignoreUriList 中的任何 URI 匹配，将 HTTP 请求的属性设置为放行，并返回 true。
                request.setAttribute(PERMIT_ALL, Boolean.TRUE);
                return true;
            }
        }
        // 如果没有找到匹配项，返回 false。
        return false;
    }

    /**
     * 从给定的 HTTP 请求中提取请求路径。
     *
     * @param request 传入的 HTTP 请求。
     * @return HTTP 请求路径。
     */
    private String getRequestPath(HttpServletRequest request) {
        String url = request.getServletPath();
        String pathInfo = request.getPathInfo();
        if (pathInfo != null) {
            url = StringUtils.hasLength(url) ? url + pathInfo : pathInfo;
        }
        return url;
    }

    public void addIgnoreUri(String uri) {
        staticListURL.add(uri);
    }
}
