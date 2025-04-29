package cc.sofast.framework.starter.common.trans;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wxl
 */
public class InMemoryKvTransStore implements KvTransStore {

    private static final Map<String, Object> KV_STORE = new ConcurrentHashMap<>();

    @Override
    public Object get(String key) {

        return KV_STORE.get(key);
    }

    public void put(String key, Object value) {
        KV_STORE.put(key, value);
    }

    public void remove(String key) {
        KV_STORE.remove(key);
    }

    public void clear() {
        KV_STORE.clear();
    }

    public Map<String, Object> getAll() {
        return KV_STORE;
    }

}
