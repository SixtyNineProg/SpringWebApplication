package by.clevertec.WebApplication.сache.impl;

import by.clevertec.WebApplication.dataSets.User;
import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class LFUCacheTest {

    private final LFUCache lfuCache = new LFUCache();
    private final User user1 = new User(1, "Vitya", "victor@mail.ru", "12345", 10);
    private final User user2 = new User(2, "Sasha", "Sasha@mail.ru", "12727", 11);
    private final User user3 = new User(3, "Xenia", "Xenia@mail.ru", "72572", 12);
    private final User user4 = new User(4, "Alexey", "Alexey@mail.ru", "45657", 13);
    private final User user5 = new User(5, "Alexey", "Alexey@mail.ru", "45657", 13);

    private void init(){
        lfuCache.setSizeCache(5);
        lfuCache.clean();
        lfuCache.addInCache(user1.getId(), Optional.of(user1));
        lfuCache.addInCache(user2.getId(), Optional.of(user2));
        lfuCache.addInCache(user3.getId(), Optional.of(user3));
        lfuCache.addInCache(user4.getId(), Optional.of(user4));
        lfuCache.addInCache(user5.getId(), Optional.of(user5));
    }

    @Test
    public void whenAddInCacheThenCacheSizeIncrement() {
        lfuCache.addInCache(1, Optional.of(user1));
        final int size = lfuCache.size();
        assertThat(size, is(1));
    }

    @Test
    public void whenAddInCacheThenCacheSizeNotIncrement() {
        init();
        lfuCache.addInCache(1, Optional.of(user1));
        final int size = lfuCache.size();
        assertThat(size, is(5));
    }

    @Test(expected = NullPointerException.class)
    public void whenAddInCacheNullUserThenNullPointerException() {
        lfuCache.addInCache(1, Optional.empty());
    }

    @Test
    public void whenGetUserOfCacheThenReceiveOptionalUser() {
        init();
        final Optional<User> user = lfuCache.get(1);
        assertTrue(user.isPresent());
    }

    @Test
    public void whenGetUserOfNullCacheThenReceiveOptionalEmpty() {
        final Optional<User> user = lfuCache.get(1);
        assertThat(user, is(Optional.empty()));
    }

    @Test
    public void whenDeleteUserByIdThenCacheSizeDecrement() {
        init();
        lfuCache.delete(1);
        final int size = lfuCache.size();
        assertThat(size, is(4));
    }

    @Test
    public void whenCleanThenCacheSizeIsZero() {
        init();
        lfuCache.clean();
        final int size = lfuCache.size();
        assertThat(size, is(0));
    }
/*
    @Test
    public void whenAddInCacheThenLessUsedItemWillBeDeleted() {
        LFUCache lfucache = new LFUCache(5);
        lfucache.setSizeCache(5);
        lfucache.clean();
        lfucache.addInCache(user1.getId(), Optional.of(user1));
        lfucache.addInCache(user2.getId(), Optional.of(user2));
        lfucache.addInCache(user3.getId(), Optional.of(user3));
        lfucache.addInCache(user4.getId(), Optional.of(user4));
        lfucache.addInCache(user5.getId(), Optional.of(user5));
        lfucache.addInCache(6, Optional.of(new User(6, "Alla", "Alla@mail.ru", "12345", 18)));
        User user = lfucache.getCacheUsers().get(1);
        assertNull(user);
    }
 */
}