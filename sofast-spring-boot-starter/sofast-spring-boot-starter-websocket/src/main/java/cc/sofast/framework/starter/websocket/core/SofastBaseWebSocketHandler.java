package cc.sofast.framework.starter.websocket.core;

import cc.sofast.framework.starter.security.context.LoginUser;
import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.util.Map;

/**
 * websocket handler 封装
 *
 * @author wxl
 */
@Slf4j
public abstract class SofastBaseWebSocketHandler extends AbstractWebSocketHandler {

    /**
     * 连接建立成功
     *
     * @param session WebSocketSession
     * @throws Exception Exception
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        //websocket 连接创建成功后, 将session 放入缓存中
        Map<String, Object> attributes = session.getAttributes();
        Object loginUser = attributes.get(WebSocketConstants.LOGIN_USER_KEY);
        if (ObjectUtil.isNull(loginUser)) {
            session.close(CloseStatus.BAD_DATA);
            log.info("[connect] invalid token received. sessionId: {}", session.getId());
            return;
        }
        LoginUser currentUser = (LoginUser) loginUser;
        String sessionKey = sessionKey(session);
        WebSocketSessionHolder.addSession(sessionKey, session);
        log.info("[connect] sessionId: {},userId:{},userName:{}", session.getId(), currentUser.getId(), currentUser.nickName());
    }

    /**
     * 获取sessionKey
     *
     * @param session WebSocketSession
     * @return sessionKey
     */
    protected abstract String sessionKey(WebSocketSession session);

    /**
     * 连接关闭
     *
     * @param session WebSocketSession
     * @param status  CloseStatus
     * @throws Exception Exception
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        //websocket 连接关闭后, 将session 移除缓存中
    }
}
