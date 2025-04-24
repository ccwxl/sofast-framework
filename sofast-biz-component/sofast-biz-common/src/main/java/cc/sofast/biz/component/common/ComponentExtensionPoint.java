package cc.sofast.biz.component.common;

/**
 * @author wxl
 */
public interface ComponentExtensionPoint<T> {

    void addBefore(T context);

    void addAfter(T context);

    void updateBefore(T context);

    void updateAfter(T context);

    void deleteBefore(T context);

    void deleteAfter(T context);
}
