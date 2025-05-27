package cc.sofast.framework.starter.websocket.core;

import cc.sofast.framework.starter.security.context.LoginUser;
import cc.sofast.framework.starter.security.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

/**
 * @author wxl
 */
@Slf4j
public class SofastWebSocketInterceptor implements HandshakeInterceptor {

    /**
     * 握手之前执行的拦截器
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        //将用户信息放到attributes中
        LoginUser loginUser = SecurityUtils.getLoginUser();
        attributes.put(WebSocketConstants.LOGIN_USER_KEY, loginUser);
        return true;
    }

    /**
     * 握手之后执行的拦截器
     */
    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }
}
