package by.clevertec.WebApplication.controller;

import by.clevertec.WebApplication.constants.Constants;
import by.clevertec.WebApplication.dataSets.User;
import by.clevertec.WebApplication.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constants.USER)
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> saveUser(@RequestBody User user) {
        try {
            return ResponseEntity.ok(userService.save(user));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = Constants.ID_PATH_VARIABLE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
        try {
            return ResponseEntity.ok(userService.delete(id));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
