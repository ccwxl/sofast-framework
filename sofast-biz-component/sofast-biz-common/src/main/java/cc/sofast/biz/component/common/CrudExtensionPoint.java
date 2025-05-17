package cc.sofast.biz.component.common;

/**
 * @author wxl
 */
public interface CrudExtensionPoint<T> extends ComponentExtensionPoint {
    default void addBefore(T context) {

    }

    default void addAfter(T context) {

    }

    default void updateBefore(T context) {

    }

    default void updateAfter(T context) {

    }

    default void deleteBefore(T context) {

    }

    default void deleteAfter(T context) {

    }
}
