package by.clevertec.WebApplication.service.impl;

import by.clevertec.WebApplication.configs.CacheConfig;
import by.clevertec.WebApplication.constants.Constants;
import by.clevertec.WebApplication.dataSets.User;
import by.clevertec.WebApplication.repository.UserRepository;
import by.clevertec.WebApplication.service.UserService;
import by.clevertec.WebApplication.сache.Cache;
import by.clevertec.WebApplication.сache.factory.CacheFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService<User> {

    private final CacheConfig cacheConfig;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    private Cache cache;

    public UserServiceImpl(UserRepository userRepository, ObjectMapper objectMapper, CacheConfig cacheConfig) {
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
        this.cacheConfig = cacheConfig;
        cache = CacheFactory.getCache(cacheConfig.getCacheType());
    }

    @Override
    public Integer saveUser(User user) {
        userRepository.save(user);
        if (cacheConfig.getEnabled()) {
            cache.addInCache(user.getId(), Optional.of(user));
        }
        log.info(Constants.USER_SAVED, toJson(user));
        return user.getId();
    }

    @Override
    public Boolean deleteUser(Integer id) {
        if (cacheConfig.getEnabled()){
            cache.delete(id);
        }
        userRepository.deleteById(id);
        log.info(Constants.USER_DELETED, id);
        return true;
    }

    @Override
    public Optional<User> getUser(Integer id) {
        Optional<User> user;
        if (cacheConfig.getEnabled()) {
            user = cache.get(id);
            if (!user.isPresent()){
                user = userRepository.findById(id);
                cache.addInCache(id, user);
                Optional<User> finalUser1 = user;
                user.ifPresent(data -> log.info(Constants.USER_RECEIVED, id, toJson(finalUser1)));
            }
        } else {
            user = userRepository.findById(id);
        }
        Optional<User> finalUser = user;
        user.ifPresent(data -> log.info(Constants.USER_RECEIVED_CACHE, id, toJson(finalUser)));
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        log.info(Constants.USERS_RECEIVED);
        return users;
    }

    @Override
    public Boolean updateUser(User user) {
        userRepository.save(user);
        if (cacheConfig.getEnabled()) {
            cache.addInCache(user.getId(), Optional.of(user));
        }
        Optional<User> optionalUser = Optional.of(user);
        optionalUser.ifPresent(data -> log.info(Constants.USER_UPDATED, user.getId(), toJson(user)));
        return true;
    }

    @Override
    public Page<User> getUser(Integer pageSize, Integer pageNumber) {
        Page<User> users = userRepository.findAllByOrderByAge(PageRequest.of(pageNumber, pageSize));
        log.info(Constants.USER_PAGEABLE, toJson(users));
        return users;
    }

    private synchronized String toJson(Object o) {
        String json = null;
        try {
            json = objectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            log.warn(Constants.ERROR_PARSING_OF_OBJECT, o.getClass().getSimpleName(), e.toString());
        }
        return json;
    }
}