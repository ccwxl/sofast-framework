package cc.sofast.framework.starter.websocket.core;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.Ordered;

/**
 * 应用启动后订阅websocket 消息change
 *
 * @author wxl
 */
public class SofastWebSocketMessageListener implements ApplicationRunner, Ordered {

    @Override
    public void run(ApplicationArguments args) throws Exception {

    }

    @Override
    public int getOrder() {
        return -1;
    }
}
