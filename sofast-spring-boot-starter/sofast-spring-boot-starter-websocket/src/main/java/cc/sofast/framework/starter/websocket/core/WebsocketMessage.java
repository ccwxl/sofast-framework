package cc.sofast.framework.starter.websocket.core;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * webSocket 消息封装
 *
 * @author wxl
 */
@Data
public class WebsocketMessage implements Serializable {

    /**
     * 消息类型
     */
    private MessageType type = MessageType.TEXT;

    /**
     * 租户id
     */
    private String tenantId;

    /**
     * 需要推送到的session key 列表
     */
    private List<String> sessionKeys;

    /**
     * 需要发送的消息
     */
    private String message;

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
}
