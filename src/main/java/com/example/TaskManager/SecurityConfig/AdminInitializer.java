package com.example.TaskManager.SecurityConfig;

import com.example.TaskManager.Enum.UserRole;
import com.example.TaskManager.Models.UserModel;
import com.example.TaskManager.Repo.UserRepo;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminInitializer {

    @Autowired
    UserRepo repo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostConstruct
    public void initializeAdmin(){
        if(repo.findByUserRole(UserRole.ADMIN)==null){
            UserModel admin = new UserModel();
            admin.setPassword(passwordEncoder.encode("admin@123"));
            admin.setName("Admin");
            admin.setUserRole(UserRole.ADMIN);
            admin.setEmail("admin@gmail.com");
            repo.save(admin);
            System.out.println("Default Admin Created");
        }
        else
            System.out.println("Default Admin already exist");

    }
}
