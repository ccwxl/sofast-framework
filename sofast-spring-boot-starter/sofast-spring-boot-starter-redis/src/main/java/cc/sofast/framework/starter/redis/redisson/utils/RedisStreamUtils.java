package cc.sofast.framework.starter.redis.redisson.utils;

import org.redisson.api.RStream;
import org.redisson.api.StreamMessageId;
import org.redisson.api.stream.StreamAddArgs;
import org.redisson.api.stream.StreamCreateGroupArgs;
import org.redisson.api.stream.StreamReadGroupArgs;

import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * 使用redisson 封装的stream 组件
 *
 * @author wxl
 */
public class RedisStreamUtils extends RedissonBaseUtils {

    private RedisStreamUtils() {

    }

    public static <T> void streamConsumer(String streamKey, String groupName, Consumer<Map<String, T>> consumer) {
        RStream<String, T> stream = getRedissonClient().getStream(streamKey);
        StreamCreateGroupArgs createGroupArgs = StreamCreateGroupArgs.name(groupName);
        stream.createGroup(createGroupArgs);
        Thread thread = new Thread(() -> {
            while (true) {
                Map<StreamMessageId, Map<String, T>> messages = stream
                        .readGroup("group1", "consumer1", StreamReadGroupArgs.greaterThan(StreamMessageId.LAST));
                for (Map.Entry<StreamMessageId, Map<String, T>> entry : messages.entrySet()) {
                    Map<String, T> value = entry.getValue();
                    consumer.accept(value);
                }
            }
        });
        thread.setName(streamKey + "#" + groupName + "#" + UUID.randomUUID());
        thread.start();
    }

    public static void streamAdd(String streamKey) {
        RStream<String, String> stream = getRedissonClient().getStream(streamKey);
        stream.add( StreamAddArgs.entries(Map.of("key", "value")).trimNonStrict().maxLen(1).noLimit());
    }
}
