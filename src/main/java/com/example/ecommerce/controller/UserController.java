package com.example.ecommerce.controller;

import com.example.ecommerce.entity.UserData;
import com.example.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
public class UserController {


    @Autowired
    private UserService userService;

//    @PostMapping("/registerNewUser")
//    public UserData registerNewUser(@RequestBody UserData userData){
//        return userService.registerNewUser(userData);
//    }

    @PostMapping("/registerNewUser")
    public UserData registerNewUser(@RequestBody UserData user){
        return userService.registerNewUser(user);
    }

    @PostConstruct
    public void initRolesAndUser(){
        userService.initRolesAndUsers();
    }


    @GetMapping({"/forAdmin"})
    @PreAuthorize("hasRole('Admin')")
    public String forAdmin(){
        return "This url is only accessible for the Admins";
    }
    @GetMapping({"/forUser"})
    @PreAuthorize("hasRole('User')")
    public String forUser(){
        return "This url is only accessible for Users";
    }

}
