package by.clevertec.WebApplication.сache.impl;

import by.clevertec.WebApplication.dataSets.User;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class LRUCacheTest {

    private final LRUCache lruCache = new LRUCache();
    private final User user1 = new User(1, "Vitya", "victor@mail.ru", "12345", 10);
    private final User user2 = new User(2, "Sasha", "Sasha@mail.ru", "12727", 11);
    private final User user3 = new User(3, "Xenia", "Xenia@mail.ru", "72572", 12);
    private final User user4 = new User(4, "Alexey", "Alexey@mail.ru", "45657", 13);
    private final User user5 = new User(5, "Alexey", "Alexey@mail.ru", "45657", 13);

    @BeforeClass
    public void beforeWorkCache(){
        lruCache.setSizeCache(5);
        lruCache.addInCache(user1.getId(), Optional.of(user1));
        lruCache.addInCache(user2.getId(), Optional.of(user2));
        lruCache.addInCache(user3.getId(), Optional.of(user3));
        lruCache.addInCache(user4.getId(), Optional.of(user4));
        lruCache.addInCache(user5.getId(), Optional.of(user5));
    }

    @Test
    public void whenAddInCacheThenSizeHashTableIncrement() {//replase
        lruCache.addInCache(1, Optional.of(user1));
        final int size = lruCache.getCacheUsers().size();
        assertThat(size, is(1));
    }

    @Test
    public void get() {

    }

    @Test
    public void replaceTime() {
    }

    @Test
    public void searchMinTime(){

    }
}