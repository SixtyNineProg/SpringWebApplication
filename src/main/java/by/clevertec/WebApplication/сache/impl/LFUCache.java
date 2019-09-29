package by.clevertec.WebApplication.сache.impl;

import by.clevertec.WebApplication.dataSets.User;
import by.clevertec.WebApplication.сache.Cache;

import java.util.Optional;

public class LFUCache implements Cache {
    @Override
    public void addInCache(Integer id, Optional<User> user) {
    }

    @Override
    public Optional<User> get(Integer id) {
        return Optional.empty();
    }

    @Override
    public void delete(Integer id) {
    }
}
