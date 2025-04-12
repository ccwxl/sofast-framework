package cc.sofast.framework.starter.common.utils;

import java.io.Serializable;

/**
 * @author wxl
 */
@FunctionalInterface
public interface SFunction<T> extends Serializable {
    Object apply(T t);
}
