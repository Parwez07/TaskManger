package com.example.TaskManager.Services;

import com.example.TaskManager.Enum.UserRole;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepo repo;

    @Autowired
    PasswordEncoder encoder ;
    @Autowired
    AuthenticationManager manager;
    @Autowired
    JwtService jwtService;

    public ResponseEntity<?> registerUser(UserModel user){
        Optional<UserModel> userExist = repo.findByEmail(user.getEmail());
        if(userExist.isPresent()){
            if (userExist.get().getUserRole()== UserRole.ADMIN)
                return new ResponseEntity<>("Admin already exist",HttpStatus.BAD_REQUEST);
            else
                return new ResponseEntity<>("Employee already exist",HttpStatus.BAD_REQUEST);
        }
        if(user.getUserRole()==null)
            user.setUserRole(UserRole.EMPLOYEE);

        user.setPassword(encoder.encode(user.getPassword()));
        UserModel addUser = repo.save(user);
        addUser.setPassword(null);
        return new ResponseEntity<>(addUser,HttpStatus.CREATED);
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
