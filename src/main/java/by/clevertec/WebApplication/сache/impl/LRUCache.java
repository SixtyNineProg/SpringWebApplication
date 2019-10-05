package by.clevertec.WebApplication.сache.impl;

import by.clevertec.WebApplication.constants.Constants;
import by.clevertec.WebApplication.dataSets.User;
import by.clevertec.WebApplication.сache.Cache;
import lombok.extern.slf4j.Slf4j;

import java.util.Hashtable;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class LRUCache implements Cache {

    private int capacityCache = 10;
    private Hashtable<Integer, User> cacheUsers = new Hashtable<>(capacityCache);
    private Hashtable<Integer, Long> cacheTime = new Hashtable<>(capacityCache);

    public LRUCache() {
    }

    public LRUCache(int capacityCache) {
        this.capacityCache = capacityCache;
    }

    @Override
    public void addInCache(Integer id, Optional<User> user) {
        if (user.isPresent()) {
            if (cacheTime.size() == capacityCache && cacheUsers.size() == capacityCache && cacheUsers.get(id) == null) {
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

    @Override
    public void delete(Integer id) {
        cacheUsers.remove(id);
        cacheTime.remove(id);
    }

    @Override
    public void clean() {
        cacheUsers.clear();
        cacheTime.clear();
    }

    private void replaceTime(Integer id) {
        cacheTime.remove(id);
        cacheTime.put(id, System.currentTimeMillis());
    }

    private int searchMinTime() {
        AtomicInteger minKey = new AtomicInteger(cacheTime.entrySet().iterator().next().getKey());
        AtomicLong minValue = new AtomicLong(cacheTime.entrySet().iterator().next().getValue());
        cacheTime.forEach((key, value) -> {
            if (minValue.get() > value) {
                minKey.set(key);
                minValue.set(value);
            }
        });
        return minKey.get();
    }

    @Override
    public int size(){
        return cacheUsers.size();
    }
}


