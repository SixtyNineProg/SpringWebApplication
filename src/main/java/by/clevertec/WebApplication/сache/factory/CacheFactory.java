package by.clevertec.WebApplication.сache.factory;

import by.clevertec.WebApplication.сache.Cache;
import by.clevertec.WebApplication.сache.impl.LFUCache;
import by.clevertec.WebApplication.сache.impl.LRUCache;

public class CacheFactory {
    public static Cache getCache(String cacheType) {
        switch (cacheType) {
            case "LRU": {
                return new LRUCache();
            }
            case "LFU": {
                return new LFUCache();
            }
            default: {
                throw new RuntimeException();
            }
        }
    }
}
