package cc.sofast.framework.starter.redis.redisson.utils;

import org.redisson.api.RedissonClient;

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

}
