package cc.sofast.framework.starter.websocket.core;

import lombok.Data;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * webSocket 消息封装
 *
 * @author wxl
 */
@Data
public class WebsocketPushMessage implements Serializable {

    private WebsocketPushMessage() {

    }

    /**
     * 消息类型
     */
    private MessageType type = MessageType.TEXT;

    /**
     * 需要发送的消息
     */
    private String message;

    /**
     * 需要推送到的session key 列表
     */
    private List<String> sessionKeys;

    /**
     * 前缀消息,给所有符合前缀的key的session发送消息
     */
    private String prefixSessionKey;

    /**
     * 批量消息,给所有符合前缀的key的session发送消息,使用: batchSessionMsg
     */
    private Map<String, String> batchMessage;

    /**
     * 消息类型
     */
    public enum MessageType {
        /**
         * ping消息,使用: sessionKeys
         */
        PING,
        /**
         * ping消息,使用: sessionKeys
         */
        PONG,
        /**
         * 文本消息,使用: sessionKeys
         */
        TEXT,
        /**
         * 全部消息
         */
        PUSH_ALL,
        /**
         * 前缀消息,给所有符合前缀的key的session发送消息,使用: prefixSessionKey
         */
        PREFIX_TEXT,
        /**
         * 批量消息,给所有符合前缀的key的session发送消息,使用: batchMessage
         */
        BATCH_TEXT
    }

    public static WebsocketPushMessage batchMessage(Map<String, String> batchMessage) {

        return new WebsocketPushMessageBuilder()
                .type(MessageType.BATCH_TEXT)
                .batchMessage(batchMessage)
                .build();
    }

    public static WebsocketPushMessage prefixMessage(String prefixSessionKey) {

        return new WebsocketPushMessageBuilder()
                .type(MessageType.PREFIX_TEXT)
                .prefixSessionKey(prefixSessionKey)
                .build();
    }

    public static WebsocketPushMessage textMessage(String session, String message) {

        return new WebsocketPushMessageBuilder()
                .type(MessageType.TEXT)
                .sessionKeys(Collections.singletonList(session))
                .message(message)
                .build();
    }

    public static WebsocketPushMessage textMessage(List<String> sessionKeys, String message) {

        return new WebsocketPushMessageBuilder()
                .type(MessageType.TEXT)
                .message(message)
                .sessionKeys(sessionKeys)
                .build();
    }

    public static WebsocketPushMessage allMessage(String message) {

        return new WebsocketPushMessageBuilder()
                .type(MessageType.PUSH_ALL)
                .message(message)
                .build();
    }

    public static WebsocketPushMessage pingMessage(List<String> sessionKeys) {

        return new WebsocketPushMessageBuilder()
                .type(MessageType.PING)
                .sessionKeys(sessionKeys)
                .build();
    }

    public static WebsocketPushMessage pongMessage(List<String> sessionKeys) {

        return new WebsocketPushMessageBuilder()
                .type(MessageType.PONG)
                .sessionKeys(sessionKeys)
                .build();
    }


    public static class WebsocketPushMessageBuilder {
        private final WebsocketPushMessage message;

        public WebsocketPushMessageBuilder() {
            message = new WebsocketPushMessage();
        }

        public WebsocketPushMessageBuilder type(MessageType type) {
            message.setType(type);
            return this;
        }

        public WebsocketPushMessageBuilder message(String msg) {
            message.setMessage(msg);
            return this;
        }

        public WebsocketPushMessageBuilder sessionKeys(List<String> sessionKeys) {
            message.setSessionKeys(sessionKeys);
            return this;
        }

        public WebsocketPushMessageBuilder prefixSessionKey(String prefixSessionKey) {
            message.setPrefixSessionKey(prefixSessionKey);
            return this;
        }

        public WebsocketPushMessageBuilder batchMessage(Map<String, String> batchMessage) {
            message.setBatchMessage(batchMessage);
            return this;
        }

        public WebsocketPushMessage build() {
            return message;
        }
    }
}
