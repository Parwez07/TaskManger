package com.example.TaskManager.Utility;

import com.example.TaskManager.Models.UserModel;
import com.example.TaskManager.Models.UserPrincipal;
import com.example.TaskManager.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CurrentUser {

    @Autowired
    UserRepo repo;

    public UserModel getCurrentLoggedIn(){
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
