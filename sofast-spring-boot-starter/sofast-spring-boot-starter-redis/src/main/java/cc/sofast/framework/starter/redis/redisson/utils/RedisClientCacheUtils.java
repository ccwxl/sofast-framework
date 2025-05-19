package cc.sofast.framework.starter.redis.redisson.utils;

import org.redisson.api.RBucket;
import org.redisson.api.RClientSideCaching;
import org.redisson.api.options.ClientSideCachingOptions;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * using example:
 * <pre>
 *      String value = RedisClientCacheUtils.getOrLoad(
 *                 "user-cache",
 *                 "user:123",
 *                 key -> {
 *                     System.out.println("load from DB");
 *                     return "User_123_Data";
 *                 }
 *         );
 * </pre>
 * using example:
 * <per>
 * RClientSideCaching config = RedisClientCacheUtils.initCache("cc-config");
 * </per>
 * 基于redisson封装的客户端缓存 jvm +redis的两层缓存
 *
 * @author wxl
 */
public class RedisClientCacheUtils extends RedissonUtils {

    /**
     * 本地缓存
     */
    private static final ConcurrentHashMap<String, RClientSideCaching> LOCAL_CACHE = new ConcurrentHashMap<>();

    /**
     * 初始化一个 ClientSide 缓存 map（使用 Redis native tracking + 本地缓存）
     */
    public static RClientSideCaching initCache(String cacheName) {
        return LOCAL_CACHE.computeIfAbsent(cacheName, function -> {
            ClientSideCachingOptions options = ClientSideCachingOptions.defaults();
            return getRedissonClient().getClientSideCaching(options);
        });
    }


    /**
     * 获取或加载缓存（带 ClientSide 缓存）
     */
    public static <T> T getOrInitCache(String cacheName, String key, Function<String, T> loader) {
        RClientSideCaching cache = initCache(cacheName);
        RBucket<T> bucket = cache.getBucket(key);
        T value = bucket.get();
        if (value != null) {
            return value;
        }
        // 缓存未命中则加载并放入 Redis
        value = loader.apply(key);
        if (value != null) {
            bucket.set(value);
        }
        return value;
    }


    /**
     * 主动更新 Redis + 本地缓存
     */
    public static void put(String cacheName, String key, Object value) {
        RClientSideCaching cache = initCache(cacheName);
        cache.getBucket(key).set(value);
    }

    /**
     * 删除缓存
     */
    public static void invalidate(String cacheName, String key) {
        RClientSideCaching cache = LOCAL_CACHE.get(cacheName);
        if (cache != null) {
            cache.getBucket(key).delete();
        }
    }

    /**
     * 获取缓存
     */
    public static <T> T getByClientCache(RClientSideCaching caching, String key) {
        RBucket<T> bucket = caching.getBucket(key);
        return bucket.get();
    }

    /**
     * 更新缓存
     */
    public static <T> void updateByClientCache(RClientSideCaching caching, String key, T value) {
        RBucket<T> bucket = caching.getBucket(key);
        bucket.set(value);
    }
}
