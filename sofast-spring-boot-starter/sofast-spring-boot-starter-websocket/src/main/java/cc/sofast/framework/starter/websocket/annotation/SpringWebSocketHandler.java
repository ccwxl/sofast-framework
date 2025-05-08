package cc.sofast.framework.starter.websocket.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * WebSocket Handler Annotation
 *
 * @author wxl
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SpringWebSocketHandler {

    String[] value();

    String[] domain() default "*";

    Class<?>[] interceptors() default {};
}
