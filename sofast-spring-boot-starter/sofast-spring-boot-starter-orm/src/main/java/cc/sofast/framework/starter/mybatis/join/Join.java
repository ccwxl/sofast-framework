package cc.sofast.framework.starter.mybatis.join;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <a href="https://github.com/maiaimei/example/blob/main/mybatis/mybatis-spring-boot-starter-study/src/main/java/org/example/MybatisApplication.java">...</a>
 * @author wxl
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Join {
    Class<?> left();

    Class<?> right();

    String type();

    String leftKey();

    String rightKey();
}
