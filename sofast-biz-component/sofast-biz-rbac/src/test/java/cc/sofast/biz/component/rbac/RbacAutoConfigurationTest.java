package cc.sofast.biz.component.rbac;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import javax.sql.DataSource;
import java.sql.SQLException;


@Testcontainers
@SpringBootTest(classes = {
        RbacTestApp.class
}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
class RbacAutoConfigurationTest {

    @Container
    @ServiceConnection
    static MySQLContainer<?> mysql = new MySQLContainer<>(DockerImageName.parse("mysql:8.4.4"))
            .withDatabaseName("rbac")
            .withUsername("rbac")
            .withPassword("rbac")
            .withInitScript("cc/sofast/biz/component/rbac/entity/jdbcstore/tables_mysql.sql");

    @BeforeAll
    static void beforeAll() {
        mysql.start();
    }

    @AfterAll
    static void afterAll() {
        mysql.stop();
    }

    @Autowired
    private DataSource dataSource;

    @Test
    void test() throws SQLException {
        System.out.println(dataSource.getConnection().getMetaData());
        System.out.println("test exec");
    }
}