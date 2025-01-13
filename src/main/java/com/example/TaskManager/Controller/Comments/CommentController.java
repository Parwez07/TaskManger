package com.example.TaskManager.Controller.Comments;

import com.example.TaskManager.Enum.UserRole;
import com.example.TaskManager.Models.Dto.CommentDto;
import com.example.TaskManager.Models.Task;
import com.example.TaskManager.Models.UserModel;
import com.example.TaskManager.Repo.TaskRepo;
import com.example.TaskManager.Repo.UserRepo;
import com.example.TaskManager.Services.Comment.CommentService;
import com.example.TaskManager.Services.Users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.function.Supplier;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    TaskRepo taskRepo;
    @Autowired
    UserService userService;
    @Autowired
    CommentService commentService;


    @PostMapping("/task/{taskId}")
    public ResponseEntity<?> addComment(@PathVariable Long taskId, @RequestBody CommentDto commentDto){
        commentDto.setTaskId(taskId);
        System.out.println(commentDto.getContent()+" "+commentDto.getTaskId()+" "+commentDto.getUserId());
        return commentService.addComment(commentDto);

    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<?> getComments(@PathVariable Long taskId){
        return commentService.getComments(taskId);
    }

}
