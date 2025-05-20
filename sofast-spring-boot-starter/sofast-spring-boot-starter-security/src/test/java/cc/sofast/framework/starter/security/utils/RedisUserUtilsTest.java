package cc.sofast.framework.starter.security.utils;

import cc.sofast.framework.starter.common.utils.SpringUtils;
import cc.sofast.framework.starter.redis.codec.ObjectMapperWrapper;
import cc.sofast.framework.starter.security.SecurityTestApp;
import cc.sofast.framework.starter.security.context.LoginUser;
import cc.sofast.framework.starter.security.token.SecurityUserInfo;
import cc.sofast.framework.starter.security.token.SecurityUserInfoDetailService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.redis.testcontainers.RedisStackContainer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@Slf4j
@Testcontainers
@SpringBootTest(classes = {
        SecurityTestApp.class
}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RedisUserUtilsTest {

    @Container
    @ServiceConnection //代替 @DynamicPropertySource
    static RedisStackContainer redis = new RedisStackContainer(DockerImageName.parse("redis:8.0.1"));

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @BeforeAll
    static void beforeAll() {
        redis.start();
    }

    @AfterAll
    static void afterAll() {
        redis.stop();
    }

    @Test
    void getLoginUser() {
        SecurityUserInfoDetailService userInfoDetailService = new SecurityUserInfoDetailService() {

            @Override
            public SecurityUserInfo getUserInfo(Long userid) {

                return null;
            }
        };

        mockStatic(SpringUtils.class);
        when(SpringUtils.getBean(SecurityUserInfoDetailService.class)).thenReturn(userInfoDetailService);

        SecurityUserInfo loginUser = RedisUserUtils.getLoginUser(1L);
        Assertions.assertNotNull(loginUser);

        SecurityUserInfo loginUser2 = RedisUserUtils.getLoginUser(1L);
        Assertions.assertNotNull(loginUser2);
    }

    @Test
    void cleanCache() throws JsonProcessingException {
        ObjectMapperWrapper objectMapperWrapper = new ObjectMapperWrapper();
        ObjectMapper objectMapper = objectMapperWrapper.getObjectMapper();
        List<Long> list = List.of(1L, 2L, 3L);
        String s = objectMapper.writeValueAsString(list);
        log.info("list: {}", s);
    }
}