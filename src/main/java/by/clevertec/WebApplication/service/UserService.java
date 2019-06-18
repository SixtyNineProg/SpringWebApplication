package by.clevertec.WebApplication.service;

import by.clevertec.WebApplication.dataSets.User;

public interface UserService {
    String save(User user);
    Boolean delete(String id);
}