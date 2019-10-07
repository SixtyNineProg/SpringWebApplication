package by.clevertec.WebApplication.controller;

import by.clevertec.WebApplication.constants.Constants;
import by.clevertec.WebApplication.datasets.User;
import by.clevertec.WebApplication.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(Constants.USER)
public class UserController {

    private final UserService<User> userService;

    public UserController(UserService<User> userService) {
        this.userService = userService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> saveUser(@RequestBody User user) {
        try {
            if (user.getName() != null &&
                    user.getEmail() != null &&
                    user.getPassword() != null &&
                    user.getAge() != 0)
                return ResponseEntity.ok(userService.saveUser(user));
            else return new ResponseEntity<>("Incorrect data", HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = Constants.ID_PATH_VARIABLE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
        Optional optionalUser = userService.getUser(id);
        if (optionalUser.isPresent()) {
            try {
                return ResponseEntity.ok(userService.deleteUser(id));
            } catch (Exception e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = Constants.ID_PATH_VARIABLE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> findUser(@PathVariable Integer id) {
        Optional user = userService.getUser(id);
        if (user.isPresent()) {
            return ResponseEntity.ok(user);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = Constants.PATH_GET_ALL,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getAll() {
        try {
            return ResponseEntity.ok(userService.getAllUsers());
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path = Constants.PATH_UPDATE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        Optional optionalUser = userService.getUser(user.getId());
        if (optionalUser.isPresent()) {
            return ResponseEntity.ok(userService.updateUser(user));
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getUser(
            @RequestParam(name = Constants.PAGESIZE_KEY, defaultValue = Constants.PAGESIZE_VALUE, required = false)
                    Integer pageSize,
            @RequestParam(name = Constants.PAGENUMBER_KEY, defaultValue = Constants.PAGENUMBER_VALUE, required = false)
                    Integer pageNumber) {
        try {
            return ResponseEntity.ok(userService.getUser(pageSize, pageNumber));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
