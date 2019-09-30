package by.clevertec.WebApplication.сache.impl;

import by.clevertec.WebApplication.dataSets.User;
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

    private void init(){
        lruCache.setSizeCache(5);
        lruCache.clean();
        lruCache.addInCache(user1.getId(), Optional.of(user1));
        lruCache.addInCache(user2.getId(), Optional.of(user2));
        lruCache.addInCache(user3.getId(), Optional.of(user3));
        lruCache.addInCache(user4.getId(), Optional.of(user4));
        lruCache.addInCache(user5.getId(), Optional.of(user5));
    }

    @Test
    public void whenAddInCacheThenSizeCacheIncrement() {
        lruCache.addInCache(1, Optional.of(user1));
        final int size = lruCache.size();
        assertThat(size, is(1));
    }

    @Test
    public void whenAddInCacheThenSizeCacheNotIncrement() {
        init();
        lruCache.addInCache(1, Optional.of(user1));
        final int size = lruCache.size();
        assertThat(size, is(5));
    }

    @Test
    public void whenAddInCacheThenLessUsedItemWillBeDeleted() {
        LRUCache lruCache = new LRUCache(5);
        lruCache.setSizeCache(5);
        lruCache.clean();
        lruCache.addInCache(user1.getId(), Optional.of(user1));
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        lruCache.addInCache(user2.getId(), Optional.of(user2));
        lruCache.addInCache(user3.getId(), Optional.of(user3));
        lruCache.addInCache(user4.getId(), Optional.of(user4));
        lruCache.addInCache(user5.getId(), Optional.of(user5));
        lruCache.addInCache(6, Optional.of(new User(6, "Alla", "Alla@mail.ru", "12345", 18)));
        System.out.println(lruCache.getCacheUsers());
        User user = lruCache.getCacheUsers().get(1);
        assertNull(user);
    }

    @Test(expected = NullPointerException.class)
    public void whenAddInCacheNullUserThenNullPointerException() {
        lruCache.addInCache(1, Optional.empty());
    }

    @Test
    public void whenGetUserOfNullCacheThenReceiveOptionalEmpty() {
        final Optional<User> user = lruCache.get(1);
        assertThat(user, is(Optional.empty()));
    }

    @Test
    public void whenGetUserOfCacheThenReceiveOptionalUser() {
        init();
        final Optional<User> user = lruCache.get(1);
        assertTrue(user.isPresent());
    }

    @Test
    public void whenDeleteUserByIdThenSizeCacheDecrement() {
        init();
        lruCache.delete(1);
        final int size = lruCache.size();
        assertThat(size, is(4));
    }

    @Test
    public void whenCleanThenCacheSizeIsZero() {
        init();
        lruCache.clean();
        final int size = lruCache.size();
        assertThat(size, is(0));
    }
}