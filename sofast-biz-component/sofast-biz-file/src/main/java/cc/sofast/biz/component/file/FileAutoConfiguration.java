package cc.sofast.biz.component.file;

import org.dromara.x.file.storage.spring.EnableFileStorage;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author wxl
 */
@AutoConfiguration
@ComponentScan(FileAutoConfiguration.BASE_PACKAGE)
@MapperScan(FileAutoConfiguration.BASE_PACKAGE)
@EnableConfigurationProperties(FileProperties.class)
@EnableFileStorage
public class FileAutoConfiguration {
    public static final String BASE_PACKAGE = "cc.sofast.biz.component.file";

}
