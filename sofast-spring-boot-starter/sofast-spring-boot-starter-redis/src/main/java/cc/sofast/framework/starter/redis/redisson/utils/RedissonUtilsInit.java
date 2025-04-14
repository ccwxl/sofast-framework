package cc.sofast.framework.starter.redis.redisson.utils;

import org.redisson.api.RedissonClient;

/**
 * @author wxl
 */
public class RedissonUtilsInit {

    public RedissonUtilsInit(RedissonClient redissonClient) {
        RedissonBaseUtils.setRedissonClient(redissonClient);
        RedisSubscribeUtils.setRedissonClient(redissonClient);
        RedisClientCacheUtils.setRedissonClient(redissonClient);
        RedisStreamUtils.setRedissonClient(redissonClient);
    }
}
