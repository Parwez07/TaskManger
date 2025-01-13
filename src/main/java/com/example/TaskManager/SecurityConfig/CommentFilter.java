package com.example.TaskManager.SecurityConfig;

import com.example.TaskManager.Enum.UserRole;
import com.example.TaskManager.Models.Task;
import com.example.TaskManager.Models.UserModel;
import com.example.TaskManager.Repo.TaskRepo;
import com.example.TaskManager.Services.Users.UserService;
import com.example.TaskManager.Utility.CurrentUser;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class CommentFilter extends OncePerRequestFilter {

    @Autowired
    TaskRepo taskRepo;
    @Autowired
    CurrentUser currentUser;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        UserModel currentLoggedIn = currentUser.getCurrentLoggedIn();
        String path = request.getRequestURI();

        if (path.startsWith("/comment")) {
            String[] parts = path.split("/");
            Long taskId = Long.parseLong(parts[parts.length - 1]);
            Task task = taskRepo.findById(taskId).orElse(null);
            if (task == null || !isAuthorized(task, currentLoggedIn)) {
                response.setStatus(HttpStatus.FORBIDDEN.value());
                response.getWriter().write("You are not authorized to comment on this task");
                return;
            }
        }

        // If validation passes, continue the request
        filterChain.doFilter(request, response);
    }

    private boolean isAuthorized(Task task, UserModel currentLoggedIn) {
        // Only task assignee or admin can proceed
        return task.getUser().getId().equals(currentLoggedIn.getId()) ||
                currentLoggedIn.getUserRole() == UserRole.ADMIN;
    }
}
