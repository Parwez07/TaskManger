package com.example.TaskManager.Repo;

import com.example.TaskManager.Enum.UserRole;
import com.example.TaskManager.Models.UserModel;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<UserModel, Long> {

    Optional<UserModel> findByEmail(String username);

    List<UserModel> findByUserRole(UserRole userRole);
}
