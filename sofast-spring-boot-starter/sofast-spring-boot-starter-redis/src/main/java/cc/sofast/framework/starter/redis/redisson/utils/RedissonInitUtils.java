package cc.sofast.framework.starter.redis.redisson.utils;

import org.redisson.api.RedissonClient;

/**
 * @author wxl
 */
public class RedissonInitUtils {

    public RedissonInitUtils(RedissonClient redissonClient) {
        RedissonBaseUtils.setRedissonClient(redissonClient);
        RedisSubscribeUtils.setRedissonClient(redissonClient);
        RedisClientCacheUtils.setRedissonClient(redissonClient);
        RedisStreamUtils.setRedissonClient(redissonClient);
    }
}
