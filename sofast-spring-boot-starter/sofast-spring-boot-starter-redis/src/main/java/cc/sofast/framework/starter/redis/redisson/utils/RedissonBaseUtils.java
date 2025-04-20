package cc.sofast.framework.starter.redis.redisson.utils;

import org.redisson.api.*;

import java.time.Duration;
import java.util.Map;

/**
 * 需要等待项目初始化完后才能使用{@link RedissonInitUtils#RedissonInitUtils(RedissonClient)}
 *
 * @author wxl
 */
public class RedissonBaseUtils {

    private static RedissonClient redissonClient;

    public static void checkRedissonClient() {
        if (redissonClient == null) {
            throw new NullPointerException("redissonClient is null not init, please use RedissonInitUtils init.");
        }
    }

    public static void setRedissonClient(RedissonClient redissonClient) {
        RedissonBaseUtils.redissonClient = redissonClient;
    }

    public static RedissonClient getRedissonClient() {
        checkRedissonClient();
        return redissonClient;
    }

    public static <T> T getByKey(String key) {
        checkRedissonClient();
        RBucket<T> bucket = redissonClient.getBucket(key);
        return bucket.get();
    }

    /**
     * 获得key剩余存活时间
     *
     * @param key 缓存键值
     * @return 剩余存活时间
     */
    public static <T> long getTimeToLive(final String key) {
        checkRedissonClient();
        RBucket<T> rBucket = redissonClient.getBucket(key);
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
        checkRedissonClient();
        RBucket<T> bucket = redissonClient.getBucket(key);
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
        checkRedissonClient();
        RBatch batch = redissonClient.createBatch();
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
        checkRedissonClient();
        RMap<String, T> rMap = redissonClient.getMap(key);
        return rMap.getAll(rMap.keySet());
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
        checkRedissonClient();
        RMap<Object, T> map = redissonClient.getMap(key);
        return map.get(hashKey);
    }

    /**
     * hash 数据类型
     *
     * @param key key
     * @param <T> value
     */
    public static <T> void updateHash(String key, Map<String, T> map) {
        checkRedissonClient();
        RMap<String, T> rMap = redissonClient.getMap(key);
        rMap.putAll(map);
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
    public static <T> T updateHash(String key, String hashKey, T value) {
        checkRedissonClient();
        RMap<String, T> rMap = redissonClient.getMap(key);
        return rMap.put(hashKey, value);
    }
}
