package com.example.TaskManager.Controller;

import com.example.TaskManager.Models.UserModel;
import com.example.TaskManager.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    UserService service;

    @PostMapping("/addUser")
    public ResponseEntity<?> registerUser(@RequestBody UserModel user){
       return new ResponseEntity<>(service.registerUser(user),HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserModel user){
        return new ResponseEntity<>(service.verify(user),HttpStatus.OK);
    }
}
