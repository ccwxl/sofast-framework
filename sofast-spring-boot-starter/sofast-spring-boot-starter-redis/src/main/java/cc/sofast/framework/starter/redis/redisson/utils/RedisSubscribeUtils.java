package cc.sofast.framework.starter.redis.redisson.utils;

import org.redisson.api.RTopic;

import java.util.function.Consumer;

/**
 * 使用redisson 封装的订阅工具类
 *
 * @author wxl
 */
public class RedisSubscribeUtils extends RedissonBaseUtils {

    private RedisSubscribeUtils() {

    }

    /**
     * 订阅通道消息
     *
     * @param channelKey 通道key
     * @param clazz      接收数据类型
     * @param consumer   自定义处理
     */
    public static <T> int subscribe(String channelKey, Class<T> clazz, Consumer<T> consumer) {
        checkRedissonClient();
        RTopic topic = getRedissonClient().getTopic(channelKey);
        return topic.addListener(clazz, (channel, msg) -> consumer.accept(msg));
    }

    /**
     * 发布通道消息
     *
     * @param channelKey 通道key
     * @param msg        发送数据
     * @param consumer   自定义处理
     */
    public static <T> Long publish(String channelKey, T msg, Consumer<T> consumer) {
        checkRedissonClient();
        RTopic topic = getRedissonClient().getTopic(channelKey);
        long receivedClient = topic.publish(msg);
        consumer.accept(msg);
        return receivedClient;
    }

    /**
     * 发布通道消息
     *
     * @param channelKey 通道key
     * @param msg        发送数据
     */
    public static <T> Long publish(String channelKey, T msg) {
        checkRedissonClient();
        RTopic topic = getRedissonClient().getTopic(channelKey);
        return topic.publish(msg);
    }
}
