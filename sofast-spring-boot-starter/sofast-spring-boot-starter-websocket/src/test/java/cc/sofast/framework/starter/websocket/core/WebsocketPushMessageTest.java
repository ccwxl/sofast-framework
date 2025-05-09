package cc.sofast.framework.starter.websocket.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author wxl
 */
class WebsocketPushMessageTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testAllMessage() throws JsonProcessingException {
        WebsocketPushMessage message = WebsocketPushMessage.allMessage("hello world");
        String jsonMsg = objectMapper.writeValueAsString(message);
        System.out.println(jsonMsg);
        WebsocketPushMessage websocketPushMessage = objectMapper.readValue(jsonMsg, WebsocketPushMessage.class);
        assertEquals(WebsocketPushMessage.MessageType.PUSH_ALL, websocketPushMessage.getType());
        System.out.println(websocketPushMessage);
    }
}