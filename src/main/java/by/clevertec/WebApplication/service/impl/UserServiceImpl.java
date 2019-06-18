package by.clevertec.WebApplication.service.impl;

import by.clevertec.WebApplication.dataSets.User;
import by.clevertec.WebApplication.repository.UserRepository;
import by.clevertec.WebApplication.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService<User> {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String saveUser(User user) {
        userRepository.save(user);
        return user.getId();
    }

    @Override
    public Boolean deleteUser(String id) {
        userRepository.deleteById(id);
        return true;
    }

    @Override
    public Optional<User> getUser(String id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Boolean updateUser(User user) {
        userRepository.save(user);
        return true;
    }

}