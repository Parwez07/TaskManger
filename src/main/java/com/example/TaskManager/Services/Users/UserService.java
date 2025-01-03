package com.example.TaskManager.Services.Users;

import com.example.TaskManager.Enum.UserRole;
import com.example.TaskManager.Models.Dto.UserDTO;
import com.example.TaskManager.Models.UserModel;
import com.example.TaskManager.Models.UserPrincipal;
import com.example.TaskManager.Repo.UserRepo;
import com.example.TaskManager.Services.Auth.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepo repo;

    @Autowired
    PasswordEncoder encoder;
    @Autowired
    AuthenticationManager manager;
    @Autowired
    JwtService jwtService;

    public ResponseEntity<?> registerUser(UserModel user) {
        Optional<UserModel> userExist = repo.findByEmail(user.getEmail());
        if (userExist.isPresent()) {
            return new ResponseEntity<>("Email already registered", HttpStatus.BAD_REQUEST);
        }
        if (user.getUserRole() == null)
            user.setUserRole(UserRole.EMPLOYEE);

        user.setPassword(encoder.encode(user.getPassword()));
        UserModel addUser = repo.save(user);
        addUser.setPassword(null);
        return new ResponseEntity<>(addUser, HttpStatus.CREATED);
    }

    public ResponseEntity<?> verify(UserModel user) {

        Authentication authentication =
                manager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

        if (authentication.isAuthenticated())
            return new ResponseEntity<>(jwtService.generateToken(user.getEmail()), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    public ResponseEntity<?> getUsers() {
        List<UserDTO> usersList = repo.findByUserRole(UserRole.EMPLOYEE)
                .stream()
                .map(user -> new UserDTO(user.getId(), user.getEmail(), user.getName(), user.getUserRole()))
                .toList();
        return new ResponseEntity<>(usersList, HttpStatus.OK);
    }

    public  UserModel getCurrentLoggedIn(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication!=null && authentication.isAuthenticated()){
            UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
            UserModel userModel = principal.getUserModel();
            Optional<UserModel> user = repo.findById(userModel.getId());
            return user.orElse(null);
        }
        return null;
    }
}
