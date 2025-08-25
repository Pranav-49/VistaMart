package com.pranav.eCommerce.controller;

import com.pranav.eCommerce.model.User;
import com.pranav.eCommerce.service.UserService;
import jakarta.persistence.GeneratedValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/users")
@CrossOrigin("*")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("register")
    public User registerUser(@RequestBody User user)
    {
        return userService.registerUser(user);
    }

    @PostMapping("login")
    public User loginUser(@RequestBody User user)
    {
        return userService.loginUser(user.getEmail(), user.getPassword());
    }

    @GetMapping("/getAll")
    public List<User> getAllUser()
    {
        return userService.getAllUser();
    }
}
