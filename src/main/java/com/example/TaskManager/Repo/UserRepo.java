package com.example.TaskManager.Repo;

import com.example.TaskManager.Enum.UserRole;
import com.example.TaskManager.Models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<UserModel, Long> {

    Optional<UserModel> findByEmail(String username);

    UserModel findByUserRole(UserRole userRole);
}
