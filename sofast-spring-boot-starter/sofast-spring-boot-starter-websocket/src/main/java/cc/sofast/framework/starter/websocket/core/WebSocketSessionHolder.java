package cc.sofast.framework.starter.websocket.core;

import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wxl
 */
public class WebSocketSessionHolder {

    private static final Map<String, WebSocketSession> USER_SESSION_MAP = new ConcurrentHashMap<>();

    public static void put(String sessionKey, WebSocketSession webSocketSession) {
        USER_SESSION_MAP.put(sessionKey, webSocketSession);
    }

    public static void addSession(String s, WebSocketSession session) {

    }
}
