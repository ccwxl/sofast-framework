package cc.sofast.framework.starter.common.trans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wxl
 */
public class KvTransformer<T extends Serializable> implements Transformer<T, Trans> {

    private final List<KvTransStore> kvTransStore = new ArrayList<>();

    public KvTransformer() {
        kvTransStore.add(new InMemoryKvTransStore());
    }

    @Override
    public String transform(T originalValue, Trans annotation) {
        if (originalValue == null) {
            return null;
        }
        for (KvTransStore kvTransStore : kvTransStore) {
            Object value = kvTransStore.get(originalValue.toString());
            if (value != null) {
                return value.toString();
            }
        }
        return originalValue.toString();
    }

    public void addKvTransStore(KvTransStore kvTransStore) {
        this.kvTransStore.add(kvTransStore);
    }
}
