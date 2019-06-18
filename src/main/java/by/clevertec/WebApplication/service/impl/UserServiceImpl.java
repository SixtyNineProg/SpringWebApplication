package by.clevertec.WebApplication.service.impl;

import by.clevertec.WebApplication.dataSets.User;
import by.clevertec.WebApplication.repository.UserRepository;
import by.clevertec.WebApplication.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String save(User user) {
        userRepository.save(user);
        return user.getId();
    }

    @Override
    public Boolean delete(String id) {
        userRepository.deleteById(id);
        return true;
    }
}