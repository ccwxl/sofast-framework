package cc.sofast.framework.starter.redis;

import com.redis.testcontainers.RedisContainer;
import com.redis.testcontainers.RedisStackContainer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.org.checkerframework.checker.units.qual.A;

@Testcontainers
@SpringBootTest(classes = {
        RedisTestApp.class
}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
class SofastRedisAutoConfigurationTest {

    @Container
    @ServiceConnection //代替 @DynamicPropertySource
    static RedisStackContainer redis = new RedisStackContainer(RedisContainer.DEFAULT_IMAGE_NAME.withTag(RedisContainer.DEFAULT_TAG));

    @BeforeAll
    static void beforeAll() {
        redis.start();
    }

    @AfterAll
    static void afterAll() {
        redis.stop();
    }

    @Autowired
    public RedisTemplate<?, ?> redisTemplate;

    @Autowired
    public RedisTemplate<String, Object> redisTemplateObject;

    @Autowired
    public StringRedisTemplate stringRedisTemplate;

    @Autowired
    public RedissonClient redissonClient;

    @Test
    public void test() {
        Assertions.assertNotNull(redisTemplate);
        Assertions.assertNotNull(redisTemplateObject);
        Assertions.assertNotNull(stringRedisTemplate);
        Assertions.assertNotNull(redissonClient);
        String key = "uid:1000";
        String value = "zhangsan";

        redisTemplateObject.opsForValue().set(key, value);

        Object tmpValueObj = redisTemplateObject.opsForValue().get(key);
        String tmpValue = String.valueOf(tmpValueObj);

        RBucket<String> bucket = redissonClient.getBucket(key);
        String rsValue = bucket.get();

        Assertions.assertEquals(value, tmpValue);
        Assertions.assertEquals(value, rsValue);
        Assertions.assertEquals(tmpValue, rsValue);
    }
}