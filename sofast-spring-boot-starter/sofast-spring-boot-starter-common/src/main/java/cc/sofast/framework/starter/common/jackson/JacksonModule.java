package cc.sofast.framework.starter.common.jackson;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import cc.sofast.framework.starter.common.constant.SofastConstant;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * 自定义序列化规则
 *
 * @author Administrator
 */
public class JacksonModule extends SimpleModule {

    public JacksonModule() {
        super();
        /*
        大整数转字符串, 避免精度丢失问题
         */
        this.addSerializer(Long.class, ToStringSerializer.instance);
        this.addSerializer(Long.TYPE, ToStringSerializer.instance);
        this.addSerializer(BigInteger.class, ToStringSerializer.instance);
        this.addSerializer(BigDecimal.class, ToStringSerializer.instance);

        /*
        时间相关；时区跟随JVM设置
        */
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(SofastConstant.Jackson.DATE_TIME_FORMAT);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(SofastConstant.Jackson.DATE_FORMAT);
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(SofastConstant.Jackson.TIME_FORMAT);
        this.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(dateTimeFormatter));
        this.addSerializer(LocalDate.class, new LocalDateSerializer(dateFormatter));
        this.addSerializer(LocalTime.class, new LocalTimeSerializer(timeFormatter));
        this.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(dateTimeFormatter));
        this.addDeserializer(LocalDate.class, new LocalDateDeserializer(dateFormatter));
        this.addDeserializer(LocalTime.class, new LocalTimeDeserializer(timeFormatter));
    }

}
