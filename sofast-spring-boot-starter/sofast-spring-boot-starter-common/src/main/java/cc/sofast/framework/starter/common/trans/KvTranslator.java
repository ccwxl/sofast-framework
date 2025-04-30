package cc.sofast.framework.starter.common.trans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wxl
 */
public class KvTranslator<T extends Serializable> implements Translator<T, Trans> {

    private final List<KvTransStore> kvTransStore = new ArrayList<>();

    public KvTranslator() {
        kvTransStore.add(new InMemoryKvTransStore());
    }

    @Override
    public String transform(T refVal, Trans annotation) {
        if (refVal == null) {
            return null;
        }
        for (KvTransStore kvTransStore : kvTransStore) {
            Object value = kvTransStore.get(refVal.toString());
            if (value != null) {
                return value.toString();
            }
        }
        return refVal.toString();
    }

    public void addKvTransStore(KvTransStore kvTransStore) {
        this.kvTransStore.add(kvTransStore);
    }
}
