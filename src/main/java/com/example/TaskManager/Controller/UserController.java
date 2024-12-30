package com.example.TaskManager.Controller;

import com.example.TaskManager.Enum.UserRole;
import com.example.TaskManager.Models.UserModel;
import com.example.TaskManager.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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

    @PostMapping("/admin/addAdmin")
    public ResponseEntity<?> registerAdmin(@RequestBody UserModel admin, Authentication authentication){

        if(!authentication.getAuthorities().contains(new SimpleGrantedAuthority(UserRole.ADMIN.name())))
            return new ResponseEntity<>("Bhaag bsdk yaha se.. ",HttpStatus.FORBIDDEN);

        if(admin.getUserRole()==null)
            admin.setUserRole(UserRole.ADMIN);

        return new ResponseEntity<>(service.registerUser(admin),HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserModel user){
        return new ResponseEntity<>(service.verify(user),HttpStatus.OK);
    }
}
