package by.clevertec.WebApplication.service;

import by.clevertec.WebApplication.dataSets.User;

import java.util.List;
import java.util.Optional;

public interface UserService<User> {
    String save(User user);
    Boolean delete(String id);
    Optional<User> getUser(String id);
    List<User> getAll();
}