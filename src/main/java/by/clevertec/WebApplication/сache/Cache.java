package by.clevertec.WebApplication.сache;

import by.clevertec.WebApplication.dataSets.User;

import java.util.Optional;

public interface Cache {
    void addInCache(Integer id, Optional<User> user);
    Optional<User> get(Integer id);
}
