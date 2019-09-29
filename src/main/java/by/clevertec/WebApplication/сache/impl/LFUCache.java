package by.clevertec.WebApplication.сache.impl;

import by.clevertec.WebApplication.configs.CacheConfig;
import by.clevertec.WebApplication.dataSets.User;
import by.clevertec.WebApplication.сache.Cache;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Optional;

public class LFUCache implements Cache {
    private int sizeCache = 10;
    private Hashtable<Integer, User> cacheUsers = new Hashtable<>(sizeCache);
    private HashMap<Integer, Long> cacheNumUses = new HashMap<>(sizeCache);

    public LFUCache(){
        CounterReset counterReset = new CounterReset(cacheNumUses);
        counterReset.start();
    }

    @Override
    public void addInCache(Integer id, Optional<User> user) {
        /*
        if (user.isPresent()) {
            if (cacheNumUses.size() == sizeCache && cacheUsers.size() == sizeCache) {
                Integer removeKey = searchMinNumUses();
                cacheUsers.remove(removeKey);
                cacheTime.remove(removeKey);
            }
            cacheTime.put(id, System.currentTimeMillis());
            cacheUsers.put(id, user.get());
        } else {
            log.info(Constants.USER_MISSING);
            throw new NullPointerException(Constants.USER_MISSING);
        }

         */
    }

    private Integer searchMinNumUses() {
        return null;
    }

    @Override
    public Optional<User> get(Integer id) {
        User user = cacheUsers.get(id);
        if (user != null){
            //replaceTime(id);
            return Optional.of(user);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void delete(Integer id) {
        cacheUsers.remove(id);
        cacheNumUses.remove(id);
    }

    static class CounterReset extends Thread{
        private HashMap<Integer, Long> hashMap;
        private CacheConfig cacheConfig = new CacheConfig();

        CounterReset(HashMap<Integer, Long> cacheNumUses){
            this.hashMap = cacheNumUses;
        }

        @Override
        public void run(){
            try {
                while (true) {
                    Thread.sleep(cacheConfig.getCacheResetFrequency());
                    hashMap.replaceAll((k, v) -> (long) 0);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

