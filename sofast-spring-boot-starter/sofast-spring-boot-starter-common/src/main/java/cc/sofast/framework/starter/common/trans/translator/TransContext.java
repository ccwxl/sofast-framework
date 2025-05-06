package cc.sofast.framework.starter.common.trans.translator;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 转换上下文
 *
 * @author wxl
 */
public class TransContext {
    // 最大允许的递归深度，避免栈溢出
    private static final int MAX_RECURSIVE_DEPTH = 1;
    private final Map<Integer, AtomicInteger> classRecursiveCountMap = new HashMap<>();

    /**
     * 增加递归层级并检查是否超过限制
     *
     * @return 是否超过最大深度
     */
    public boolean enter(Integer aClass) {
        AtomicInteger count = classRecursiveCountMap.get(aClass);
        return count != null && count.incrementAndGet() > MAX_RECURSIVE_DEPTH;
    }

    /**
     * 减少递归层级
     */
    public void exit() {
        classRecursiveCountMap.clear();
    }

    /**
     * 递增递归层级
     */
    public void increment(Integer address) {
        AtomicInteger atomicInteger = classRecursiveCountMap.computeIfAbsent(address, k -> new AtomicInteger(0));
        atomicInteger.incrementAndGet();
    }
}
