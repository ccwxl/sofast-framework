package cc.sofast.framework.starter.web.listener;

import cn.hutool.core.text.CharSequenceUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 项目启动事件通知
 *
 * @author Zhu JW
 * @author wxl
 **/
@Slf4j
public class LaunchEventListener {

    @Async
    @Order
    @EventListener(WebServerInitializedEvent.class)
    public void afterStart(WebServerInitializedEvent event) {
        Environment env = event.getApplicationContext().getEnvironment();
        int port = event.getWebServer().getPort();

        String protocol = "http";
        if (env.getProperty("server.ssl.key-store") != null) {
            protocol = "https";
        }

        String contextPath = env.getProperty("server.servlet.context-path");
        if (CharSequenceUtil.isBlank(contextPath)) {
            contextPath = "/doc.html";
        }

        String externalHost = getExternalHost();

        log.info(buildStartupMessage(env, protocol, port, contextPath, externalHost));
    }

    private String getExternalHost() {
        String externalHost = "localhost";
        try {
            externalHost = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.warn("获取外部(局域网/公网)IP失败");
        }
        return externalHost;
    }

    private String buildStartupMessage(Environment env, String protocol, int port, String contextPath, String externalHost) {
        String swaggerPath = "/swagger-ui/index.html";
        return CharSequenceUtil.format(
                """
                        \n----------------------------------------------------------\
                        \n\tApplication '{}' is running! Access ApiDoc URLs:\
                        \n\t ApiDoc Local: \t\t\t{}://127.0.0.1:{}{}\
                        \n\t ApiDoc External: \t\t{}://{}:{}{}\
                        \n\t SwaggerUI Local: \t\t{}://127.0.0.1:{}{}\
                        \n\t SwaggerUI External: \t{}://{}:{}{}\
                        \n----------------------------------------------------------\
                        """,
                env.getProperty("spring.application.name"),
                protocol, port, contextPath,
                protocol, externalHost, port, contextPath,
                protocol, port, swaggerPath,
                protocol, externalHost, port, swaggerPath
        );
    }
}