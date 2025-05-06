package cc.sofast.framework.starter.common.trans.translator;

import java.util.List;

/**
 * 实现java.util.RandomAccess 的集合翻译器,例如ArrayList 可以优化元素的获取速度.
 * 其他的使用 CollectionSerializerTranslator 使用Iterator 遍历
 *
 * @author wxl
 */
public class IndexListSerializerTranslator extends SerializerTranslator<List<?>> {

    @Override
    public void serialize(List<?> value, TransContext transContext) {

    }
}
