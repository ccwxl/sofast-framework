package cc.sofast.framework.starter.web.jackson;

import cc.sofast.framework.starter.common.constant.SofastConstant;
import cc.sofast.framework.starter.common.jackson.EnumModule;
import cc.sofast.framework.starter.common.jackson.JacksonModule;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.StreamReadFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.function.Consumer;

/**
 * 序列化时间格式为字符串还是时间戳?
 *
 * @author wxl
 */
public class JacksonBuilderCustomizer implements Jackson2ObjectMapperBuilderCustomizer {

    @Override
    public void customize(Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {
        jacksonObjectMapperBuilder.dateFormat(new SimpleDateFormat(SofastConstant.Jackson.DATE_TIME_FORMAT));
        // 支持构造函数注入
        jacksonObjectMapperBuilder.modulesToInstall(modules -> modules.add(new ParameterNamesModule()));
        // 支持Java8
        jacksonObjectMapperBuilder.modulesToInstall(modules -> modules.add(new Jdk8Module()));
        // 自定义的枚举
        jacksonObjectMapperBuilder.modulesToInstall(modules -> modules.add(new EnumModule()));
        // 支持时间类型
        jacksonObjectMapperBuilder.modulesToInstall(modules -> modules.add(new JavaTimeModule()));
        // 自定义规则
        jacksonObjectMapperBuilder.modulesToInstall(modules -> modules.add(new JacksonModule()));
    }
}
