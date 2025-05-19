package cc.sofast.framework.starter.security.utils;

import cc.sofast.framework.starter.common.utils.SpringUtils;
import cc.sofast.framework.starter.security.SecurityTestApp;
import cc.sofast.framework.starter.security.context.LoginUser;
import cc.sofast.framework.starter.security.token.UserInfoDetailService;
import com.redis.testcontainers.RedisStackContainer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@Testcontainers
@SpringBootTest(classes = {
        SecurityTestApp.class
}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RedisUserUtilsTest {

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

    @Test
    void getLoginUser() {
        UserInfoDetailService userInfoDetailService = new UserInfoDetailService() {
            @Override
            public List<String> getPermissions(Long userid) {
                return List.of("1", "2", "3");
            }

            @Override
            public List<String> getRoles(Long userid) {
                return List.of("4", "5", "6");
            }

            @Override
            public List<Long> getOrgs(Long userid) {
                return List.of(7L, 8L, 9L);
            }

            @Override
            public Map<String, Object> getUserInfo(Long userid) {

                return Map.of("a", "b");
            }
        };

        mockStatic(SpringUtils.class);
        when(SpringUtils.getBean(UserInfoDetailService.class)).thenReturn(userInfoDetailService);

        LoginUser loginUser = RedisUserUtils.getLoginUser(1L);
        Assertions.assertNotNull(loginUser);

        LoginUser loginUser2 = RedisUserUtils.getLoginUser(1L);
        Assertions.assertNotNull(loginUser2);

    }

    @Test
    void cleanCache() {
    }
}