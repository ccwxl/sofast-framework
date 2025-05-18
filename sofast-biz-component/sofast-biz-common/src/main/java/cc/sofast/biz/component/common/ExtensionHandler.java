package cc.sofast.biz.component.common;

import cc.sofast.framework.starter.common.utils.SpringUtils;

import java.util.Map;
import java.util.function.Consumer;

/**
 * @author wxl
 */
public class ExtensionHandler {

    /**
     * 执行扩展点
     *
     * @param extensionClass 扩展点类
     * @param consumer           执行函数
     * @param <T>            扩展点类型
     */
    public static <T> void exec(Class<T> extensionClass, Consumer<T> consumer) {
        Map<String, T> beans = SpringUtils.getBeansOfType(extensionClass);
        beans.forEach((_, v) -> consumer.accept(v));
    }
}
