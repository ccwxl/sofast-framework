package cc.sofast.framework.starter.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.redis.testcontainers.RedisContainer;
import com.redis.testcontainers.RedisStackContainer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.redisson.api.*;
import org.redisson.api.options.ClientSideCachingOptions;
import org.redisson.api.search.index.FieldIndex;
import org.redisson.api.search.index.IndexInfo;
import org.redisson.api.search.index.IndexOptions;
import org.redisson.api.search.index.IndexType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Testcontainers
@SpringBootTest(classes = {
        RedisTestApp.class
}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
class SofastRedisAutoConfigurationTest {

    @Container
    @ServiceConnection //代替 @DynamicPropertySource
    static RedisStackContainer redis = new RedisStackContainer(DockerImageName.parse("redis:8.0.1"));

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

    public ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void base_Test() {
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

    @Test
    public void client_cache_Test() throws InterruptedException {
        RClientSideCaching clientSideCaching = redissonClient.getClientSideCaching(ClientSideCachingOptions.defaults());
        clientSideCaching.getBucket("key").set("value");

        Assertions.assertEquals("value", clientSideCaching.getBucket("key").get());

        redissonClient.getBucket("key2").set("value2");
        redissonClient.getBucket("key").set("value2");

        Thread.sleep(1000);

        Assertions.assertEquals("value2", clientSideCaching.getBucket("key").get());

        Assertions.assertEquals("value2", clientSideCaching.getBucket("key").get());
    }

    @Test
    public void redis_search_Test() throws JsonProcessingException {
        String key = "dev:120111";
        Map<String, Object> deviceData = new HashMap<>();
        deviceData.put("deviceId", "120111");
        deviceData.put("time", System.currentTimeMillis());
        deviceData.put("temperature", 36.5);
        deviceData.put("humidity", 66);
        deviceData.put("location", "济南");
        redisTemplateObject.opsForHash().putAll(key, deviceData);

        RMap<Object, Object> map = redissonClient.getMap(key);
        Object location = map.get("location");
        Assertions.assertEquals("济南", location);
        map.put("location", "北京");

        Object bjLocation = redisTemplateObject.opsForHash().get(key, "location");
        Assertions.assertEquals("北京", bjLocation);

        RSearch search = redissonClient.getSearch();
        String devIdx = "dev_idx";
        search.createIndex(devIdx, IndexOptions.defaults()
                        .on(IndexType.HASH)
                        .prefix(List.of("dev:")),
                FieldIndex.text("location"),
                FieldIndex.numeric("humidity"),
                FieldIndex.numeric("time")
        );

        List<String> indexes = search.getIndexes();
        for (String idx : indexes) {
            IndexInfo info = search.info(idx);
            System.out.println(objectMapper.writeValueAsString(info));
        }
    }
}