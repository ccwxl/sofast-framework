package cc.sofast.framework.starter.redis;

import com.redis.testcontainers.RedisContainer;
import com.redis.testcontainers.RedisStackContainer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest(classes = {
        RedisTestApp.class
}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
class SofastRedisAutoConfigurationTest {

    @Container
    @ServiceConnection //代替 @DynamicPropertySource
    static RedisStackContainer redis = new RedisStackContainer(RedisStackContainer.DEFAULT_IMAGE_NAME.withTag(RedisContainer.DEFAULT_TAG))
            .withEnv("HTTP_PROXY", "http://127.0.0.1:10887")
            .withEnv("HTTPS_PROXY", "http://127.0.0.1:10887");

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
    public StringRedisTemplate stringRedisTemplate;

    @Test
    public void test() {
        Assertions.assertNotNull(redisTemplate);
        Assertions.assertNotNull(stringRedisTemplate);
        System.out.println(redisTemplate);
        System.out.println(stringRedisTemplate);
    }
}