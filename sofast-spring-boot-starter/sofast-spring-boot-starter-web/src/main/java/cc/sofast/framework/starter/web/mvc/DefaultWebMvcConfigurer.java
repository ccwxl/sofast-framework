package cc.sofast.framework.starter.web.mvc;

import cc.sofast.framework.starter.web.converter.StringToDateConverter;
import cc.sofast.framework.starter.web.converter.StringToEnumConverterFactory;
import cc.sofast.framework.starter.web.converter.StringToLocalDateTimeConverter;
import cc.sofast.framework.starter.web.converter.TimeRangeParamConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.*;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 默认 WebMVC 行为自动配置类
 *
 * @author wxl
 **/
@Slf4j
@AutoConfiguration
@RequiredArgsConstructor
public class DefaultWebMvcConfigurer implements WebMvcConfigurer, WebBindingInitializer {
    private final ObjectMapper objectMapper;

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
//        resolvers.add(new SofastHandlerExceptionResolver());
    }

    /**
     * [GET]请求, 将所有参数的空格trim
     */
    @Override
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(false));
    }

    /**
     * [GET]请求, 将int值转换成枚举类
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        //移除掉默认的string到Enum的转换器,因为默认的只支持大写到Enum的转换,自定义支持小写到Enum的转换
        registry.removeConvertible(String.class, Enum.class);
        // 使用这个增强一下,但是需要配合枚举实现BaseEnum接口
        registry.addConverterFactory(new StringToEnumConverterFactory());
        registry.addConverter(new StringToDateConverter());
        registry.addConverter(new StringToLocalDateTimeConverter());
        registry.addConverter(new TimeRangeParamConverter());
    }


    /**
     * 使用 Jackson 作为JSON MessageConverter
     * 消息转换，内置断点续传，下载和字符串
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.removeIf(x -> x instanceof StringHttpMessageConverter || x instanceof AbstractJackson2HttpMessageConverter);
        converters.add(new StringHttpMessageConverter(StandardCharsets.UTF_8));
        converters.add(new ByteArrayHttpMessageConverter());
        converters.add(new ResourceHttpMessageConverter());
        converters.add(new ResourceRegionHttpMessageConverter());
        converters.add(new MappingJackson2HttpMessageConverter(objectMapper));
    }
}
