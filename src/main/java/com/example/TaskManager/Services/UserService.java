package com.example.TaskManager.Services;

import com.example.TaskManager.Models.UserModel;
import com.example.TaskManager.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepo repo;
    @Autowired
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    @Autowired
    AuthenticationManager manager;
    @Autowired
    JwtService jwtService;

    public ResponseEntity<?> registerUser(UserModel user){
        user.setPassword(encoder.encode(user.getPassword()));
        return new ResponseEntity<>(repo.save(user),HttpStatus.CREATED);
    }

    public ResponseEntity<?> verify(UserModel user) {

        Authentication authentication =
                manager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword()));

        if(authentication.isAuthenticated())
            return new ResponseEntity<>(jwtService.generateToken(user.getEmail()),HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
