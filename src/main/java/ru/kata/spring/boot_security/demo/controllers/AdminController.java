package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleSerivce;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {


    private final RoleSerivce roleSerivce;
    private final UserService userService;

    @Autowired
    public AdminController(RoleSerivce roleSerivce, UserService userService) {
        this.roleSerivce = roleSerivce;
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity<List<User>> showAllUsers() {
        return new ResponseEntity<>(userService.getListAllUsers(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<HttpStatus> saveNewUser(@RequestBody @Valid User user) {
        userService.saveUser(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") int id) {
        return new ResponseEntity<>(userService.findUserById(id), HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<HttpStatus> saveUpdateUser(@RequestBody User user,
                                 BindingResult bindingResult) {
        userService.updateUser(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }
//
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") int id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    private ResponseEntity<Map<String, String>> handleValidExceptions(MethodArgumentNotValidException methodArgumentNotValidException) {
//        Map<String, String> errors = new HashMap<>();
//        methodArgumentNotValidException.getAllErrors().forEach(error -> {
//            String fieldName = ((FieldError)error).getField();
//            String errorMsg = error.getDefaultMessage();
//            errors.put(fieldName, errorMsg);
//        });
//        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
//    }
//    @ExceptionHandler
//    private ResponseEntity<UserErrorResponse> handleException(UserNotCreatedException e) {
//        UserErrorResponse response = new UserErrorResponse(
//                e.getMessage(),
//                System.currentTimeMillis()
//        );
//        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//    }
}


