package by.clevertec.WebApplication.service;

import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface UserService<User> {
    Integer saveUser(User user);
    Boolean deleteUser(Integer id);
    Optional<User> getUser(Integer id);
    List<User> getAllUsers();
    Boolean updateUser(User user);
    Page<User> getUser(Integer pageSize, Integer pageNumber);
}