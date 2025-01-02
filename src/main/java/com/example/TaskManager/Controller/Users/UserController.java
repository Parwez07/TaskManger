package com.example.TaskManager.Controller.Users;

import com.example.TaskManager.Models.UserModel;
import com.example.TaskManager.Services.Users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    UserService service;

    @PostMapping("/addUser")
    public ResponseEntity<?> registerUser(@RequestBody UserModel user){
        ResponseEntity<?> responseEntity = service.registerUser(user);
        return new ResponseEntity<>(responseEntity.getBody(),responseEntity.getStatusCode());
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserModel user){
        return new ResponseEntity<>(service.verify(user),HttpStatus.OK);
    }

}
