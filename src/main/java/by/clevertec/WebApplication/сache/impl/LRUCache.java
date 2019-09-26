package by.clevertec.WebApplication.сache.impl;

import by.clevertec.WebApplication.constants.Constants;
import by.clevertec.WebApplication.dataSets.User;
import by.clevertec.WebApplication.сache.Cache;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Hashtable;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Data
@Slf4j
public class LRUCache implements Cache {

    private int sizeCache = 10;
    private Hashtable<Integer, User> cacheUsers = new Hashtable<>(sizeCache);
    private Hashtable<Integer, Long> cacheTime = new Hashtable<>(sizeCache);

    public LRUCache() {
    }

    public LRUCache(int sizeCache) {
        this.sizeCache = sizeCache;
    }

    @Override
    public void addInCache(Integer id, Optional<User> user) {
        if (user.isPresent()) {
            if (cacheTime.size() == sizeCache && cacheUsers.size() == sizeCache) {
                Integer removeKey = searchMinTime();
                cacheUsers.remove(removeKey);
                cacheTime.remove(removeKey);
            }
            cacheTime.put(id, System.currentTimeMillis());
            cacheUsers.put(id, user.get());
        } else {
            log.info(Constants.USER_MISSING);
            throw new NullPointerException(Constants.USER_MISSING);
        }
    }

    @Override
    public Optional<User> get(Integer id) {
        User user = cacheUsers.get(id);
        if (user != null){
            replaceTime(id);
            return Optional.of(user);
        } else {
            return Optional.empty();
        }
    }

    public void replaceTime(Integer id) {
        cacheTime.remove(id);
        cacheTime.put(id, System.currentTimeMillis());
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


