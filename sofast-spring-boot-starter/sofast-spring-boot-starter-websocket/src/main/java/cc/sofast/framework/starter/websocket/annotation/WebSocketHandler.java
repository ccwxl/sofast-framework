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
public @interface WebSocketHandler {

    /**
     * The path of the WebSocket handler.
     *
     * @return the path
     */
    String[] value();

    /**
     * The domain of the WebSocket handler.
     *
     * @return the domain
     */
    String[] domain() default "*";

    /**
     * The interceptors of the WebSocket handler.
     *
     * @return the interceptors
     */
    Class<?>[] interceptors() default {};

    /**
     * The auth type of the WebSocket handler.
     *
     * @return the auth type
     */
    boolean ignoreAuth() default false;
}
