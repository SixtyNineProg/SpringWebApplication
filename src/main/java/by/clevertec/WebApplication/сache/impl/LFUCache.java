package by.clevertec.WebApplication.сache.impl;

import by.clevertec.WebApplication.configs.CacheConfig;
import by.clevertec.WebApplication.constants.Constants;
import by.clevertec.WebApplication.datasets.User;
import by.clevertec.WebApplication.сache.Cache;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class LFUCache implements Cache {
    private int capacityCache = 10;
    private Hashtable<Integer, User> cacheUsers = new Hashtable<>(capacityCache);
    private HashMap<Integer, Integer> cacheNumUses = new HashMap<>(capacityCache);

    public LFUCache(){
        LFUReset LFUReset = new LFUReset(cacheNumUses);
        LFUReset.start();
    }

    public LFUCache(int capacityCache) {
        this.capacityCache = capacityCache;
    }

    @Override
    public synchronized void addInCache(Integer id, Optional<User> user) {
        if (user.isPresent()) {
            if (cacheNumUses.size() == capacityCache && cacheUsers.size() == capacityCache) {
                Integer removeKey = searchMinNumUses();
                cacheUsers.remove(removeKey);
                cacheNumUses.remove(removeKey);
            }
            cacheNumUses.put(id, 0);
            cacheUsers.put(id, user.get());
        } else {
            log.info(Constants.USER_MISSING);
            throw new NullPointerException(Constants.USER_MISSING);
        }
    }

    @Override
    public synchronized Optional<User> get(Integer id) {
        User user = cacheUsers.get(id);
        if (user != null){
            cacheNumUses.put(id, cacheNumUses.get(id) + 1);
            return Optional.of(user);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public synchronized void delete(Integer id) {
        cacheUsers.remove(id);
        cacheNumUses.remove(id);
    }

    @Override
    public synchronized void clean() {
        cacheNumUses.clear();
        cacheUsers.clear();
    }

    @Override
    public int size() {
        return cacheUsers.size();
    }

    static class LFUReset extends Thread{
        private HashMap<Integer, Integer> hashMap;
        private CacheConfig cacheConfig = new CacheConfig();

        LFUReset(HashMap<Integer, Integer> cacheNumUses){
            this.hashMap = cacheNumUses;
        }

        @Override
        public void run(){
            try {
                while (true) {
                    Thread.sleep(cacheConfig.getCacheResetFrequency());
                    hashMap.replaceAll((k, v) -> 0);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private Integer searchMinNumUses() {
        AtomicInteger minKey = new AtomicInteger(cacheNumUses.entrySet().iterator().next().getKey());
        AtomicLong minValue = new AtomicLong(cacheNumUses.entrySet().iterator().next().getValue());
        cacheNumUses.forEach((key, value) -> {
            if (minValue.get() > value) {
                minKey.set(key);
                minValue.set(value);
            }
        });
        return minKey.get();
    }
}

