package cc.sofast.framework.starter.websocket.core;

import cc.sofast.framework.starter.redis.redisson.utils.RedisSubscribeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.Ordered;

import java.util.function.Consumer;

/**
 * 应用启动后订阅websocket 消息change
 *
 * @author wxl
 */
@Slf4j
public class SofastWebSocketMessageListener implements ApplicationRunner, Ordered {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        RedisSubscribeUtils.subscribe(WebSocketConstants.WEB_SOCKET_TOPIC, WebsocketPushMessage.class, new InnerMessageProcessor());
        log.info("websocket message change subscribe successful: {}", WebSocketConstants.WEB_SOCKET_TOPIC);
    }

    @Override
    public int getOrder() {

        return -1;
    }

    private static class InnerMessageProcessor implements Consumer<WebsocketPushMessage> {

        @Override
        public void accept(WebsocketPushMessage websocketPushMessage) {

        }
    }
}
