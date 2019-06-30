package by.clevertec.WebApplication.service;

import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface UserService<User> {
    String saveUser(User user);
    Boolean deleteUser(String id);
    Optional<User> getUser(String id);
    List<User> getAllUsers();
    Boolean updateUser(User user);
    Page<User> getUser(Integer pageSize, Integer pageNumber);
}