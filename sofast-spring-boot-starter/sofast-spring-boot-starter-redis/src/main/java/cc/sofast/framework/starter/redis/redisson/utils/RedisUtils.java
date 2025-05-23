package cc.sofast.framework.starter.redis.redisson.utils;

import cc.sofast.framework.starter.common.utils.json.JsonUtils;
import cc.sofast.framework.starter.redis.codec.ObjectMapperWrapper;
import cc.sofast.framework.starter.redis.codec.RedissonJsonJacksonCodec;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.redisson.api.*;
import org.redisson.client.codec.Codec;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/**
 * 需要等待项目初始化完后才能使用
 *
 * @author wxl
 */
public class RedisUtils {

    private static RedissonClient redissonClient;

    private static final Map<Class<?>, Codec> CACHE_CODEC_MAP = new ConcurrentHashMap<>();

    public static Codec getCacheCodec(Class<?> clazz) {
        return CACHE_CODEC_MAP.computeIfAbsent(clazz, v -> {
            Codec codec = redissonClient.getConfig().getCodec();
            if (codec instanceof RedissonJsonJacksonCodec codecs) {
                ObjectMapper objectMapper = codecs.getObjectMapper();
                return new RedissonJsonJacksonCodec(v, objectMapper);
            }
            return new RedissonJsonJacksonCodec(v, ObjectMapperWrapper.defaultBuilder().build());
        });
    }

    public static void checkRedissonClient() {
        if (redissonClient == null) {
            throw new NullPointerException("redissonClient is null not init, please use RedissonInitUtils init.");
        }
    }

    public static void setRedissonClient(RedissonClient redissonClient) {
        RedisUtils.redissonClient = redissonClient;
    }

    public static RedissonClient getRedissonClient() {
        checkRedissonClient();
        return redissonClient;
    }

    public static <T> T getByKey(String key, Class<T> clazz) {
        RBucket<T> bucket = getRedissonClient().getBucket(key, getCacheCodec(clazz));
        return bucket.get();
    }

    public static <T> T getByKey(String key) {
        RBucket<T> bucket = getRedissonClient().getBucket(key);
        return bucket.get();
    }

    /**
     * 获得key剩余存活时间
     *
     * @param key 缓存键值
     * @return 剩余存活时间
     */
    public static <T> long getTimeToLive(final String key) {
        RBucket<T> rBucket = getRedissonClient().getBucket(key);
        return rBucket.remainTimeToLive();
    }


    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key   缓存的键值
     * @param value 缓存的值
     */
    public static <T> void setKv(final String key, final T value) {
        setKv(key, value, false);
    }

    /**
     * 缓存基本的对象，保留当前对象 TTL 有效期
     *
     * @param key       缓存的键值
     * @param value     缓存的值
     * @param isSaveTtl 是否保留TTL有效期(例如: set之前ttl剩余90 set之后还是为90)
     * @since Redis 6.X 以上使用 setAndKeepTTL 兼容 5.X 方案
     */
    public static <T> void setKv(final String key, final T value, final boolean isSaveTtl) {
        RBucket<T> bucket = getRedissonClient().getBucket(key);
        if (isSaveTtl) {
            try {
                bucket.setAndKeepTTL(value);
            } catch (Exception e) {
                long timeToLive = bucket.remainTimeToLive();
                setKv(key, value, Duration.ofMillis(timeToLive));
            }
        } else {
            bucket.set(value);
        }
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key      缓存的键值
     * @param value    缓存的值
     * @param duration 时间
     */
    public static <T> void setKv(final String key, final T value, final Duration duration) {
        RBatch batch = getRedissonClient().createBatch();
        RBucketAsync<T> bucket = batch.getBucket(key);
        bucket.setAsync(value);
        bucket.expireAsync(duration);
        batch.execute();
    }

    /**
     * hash 数据类型
     *
     * @param key key
     * @param <T> value
     * @return map
     */
    public static <T> Map<String, T> getHash(String key) {
        RMap<String, T> rMap = getRedissonClient().getMap(key);
        return rMap.readAllMap();
    }

    /**
     * hash 数据类型
     *
     * @param key     key
     * @param hashKey hashKey
     * @param <T>     value
     * @return value
     */
    public static <T> T getHash(String key, String hashKey) {
        RMap<Object, T> map = getRedissonClient().getMap(key);
        return map.get(hashKey);
    }

    /**
     * hash 数据类型
     *
     * @param key key
     * @param <T> value
     */
    public static <T> void setHash(String key, Map<String, T> map) {
        RMap<String, T> rMap = getRedissonClient().getMap(key);
        rMap.putAll(map);
    }

    /**
     * hash 数据类型
     *
     * @param key   key
     * @param value value
     */
    public static void setHash(String key, Object value) {
        RMap<String, Object> rMap = getRedissonClient().getMap(key);
        Map<String, Object> map = JsonUtils.toMap(value);
        rMap.putAll(map);
    }

    /**
     * hash 数据类型
     *
     * @param key key
     * @param <T> value
     * @return value
     */
    public static <T> T getHash(String key, Class<T> clazz) {
        RMap<Object, Object> map = getRedissonClient().getMap(key);
        Map<Object, Object> data = map.readAllMap();
        return JsonUtils.convertValue(data, clazz);
    }

    /**
     * hash 数据类型
     *
     * @param key     key
     * @param hashKey hashKey
     * @param value   value
     * @param <T>     value
     * @return value
     */
    public static <T> T setHash(String key, String hashKey, T value) {
        RMap<String, T> rMap = getRedissonClient().getMap(key);
        return rMap.put(hashKey, value);
    }


    /**
     * 订阅通道消息
     *
     * @param channelKey 通道key
     * @param clazz      接收数据类型
     * @param consumer   自定义处理
     * @return unique listener id
     */
    public static <T> int subscribe(String channelKey, Class<T> clazz, Consumer<T> consumer) {
        RTopic topic = getRedissonClient().getTopic(channelKey, getCacheCodec(clazz));
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
        RTopic topic = getRedissonClient().getTopic(channelKey);
        return topic.publish(msg);
    }

    /**
     * 取消订阅
     *
     * @param channelKey 通道key
     * @param listenerId 监听器id
     */
    public static void unsubscribe(String channelKey, int listenerId) {
        RTopic topic = getRedissonClient().getTopic(channelKey);
        topic.removeListener(listenerId);
    }
}
