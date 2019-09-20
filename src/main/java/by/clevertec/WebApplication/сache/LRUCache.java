package by.clevertec.WebApplication.сache;

import by.clevertec.WebApplication.constants.Constants;
import by.clevertec.WebApplication.dataSets.User;

import java.util.Hashtable;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class LRUCache {
    private Hashtable<Integer, Optional<User>> cacheUsers = new Hashtable<>(Constants.sizeCache);
    private Hashtable<Integer, Long> cacheTime = new Hashtable<>(Constants.sizeCache);

    public void addInCache(Integer id, Optional<User> user) {
        if (cacheTime.size() == Constants.sizeCache && cacheUsers.size() == Constants.sizeCache) {
            Integer removeKey = searchMinTime();
            cacheUsers.remove(removeKey);
            cacheTime.remove(removeKey);
        }
        cacheTime.put(id, System.currentTimeMillis());
        cacheUsers.put(id, user);
    }

    public void replaceInPriorityQueue(Integer id) {
        cacheTime.remove(id);
        cacheTime.put(id, System.currentTimeMillis());
    }

    public Hashtable<Integer, Optional<User>> getCacheUsers() {
        return cacheUsers;
    }

    private int searchMinTime() {
        AtomicInteger minKey = new AtomicInteger();
        AtomicLong minValue = new AtomicLong(System.currentTimeMillis());
        cacheTime.forEach((key, value) -> {
            if (minValue.get() > value) {
                minKey.set(key);
                minValue.set(value);
            }
        });
        return minKey.get();
    }

}


