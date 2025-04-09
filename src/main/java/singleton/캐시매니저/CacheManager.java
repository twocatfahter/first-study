package singleton.캐시매니저;


import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class CacheManager {
    private static final CacheManager INSTANCE = new CacheManager();

    // 캐시 저장소
    // ConcurrentHashMap 멀티스레드 환경에서 안전하게 동작하는 Map
    private final Map<String, Object> cache;
    private final long defaultExpirationTime;

    private CacheManager() {
        this.cache = new ConcurrentHashMap<>();
        this.defaultExpirationTime = 3600000;
    }

    public static CacheManager getInstance() {
        return INSTANCE;
    }

    public void put(String key, Object value) {
        put(key, value, defaultExpirationTime);
    }

    public void put (String key, Object value, long expirationTime) {
        if (key == null) {
            throw new IllegalArgumentException("캐시의 키는 null 일 수 없습니다");
        }

        CacheItem cacheItem = new CacheItem(value, System.currentTimeMillis() + expirationTime);
        cache.put(key, cacheItem);
    }

    public Object get(String key) {
        if (key == null) {
            return null;
        }

        Object item = cache.get(key);
        if (item == null) {
            return null;
        }

        if (item instanceof CacheItem) {
            CacheItem cacheItem = (CacheItem) item;

            if (cacheItem.isExpired()) {
                // 만료가 되었으면
                cache.remove(key);
                return null;
            }

            return cacheItem.getValue();
        }

        return item;
    }


    private static class CacheItem {
        private final Object value;
        private final long expirationTime;

        public CacheItem(Object value, long expirationTime) {
            this.value = value;
            this.expirationTime = expirationTime;
        }

        public Object getValue() {
            return value;
        }

        public long getExpirationTime() {
            return expirationTime;
        }

        public boolean isExpired() {
            return System.currentTimeMillis() > expirationTime;
        }
    }

}
