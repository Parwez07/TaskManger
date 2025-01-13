package com.example.TaskManager.Services.Comment;

import com.example.TaskManager.Models.Comments;
import com.example.TaskManager.Models.Dto.CommentDto;
import com.example.TaskManager.Models.Task;
import com.example.TaskManager.Models.UserModel;
import com.example.TaskManager.Repo.CommentRepo;
import com.example.TaskManager.Repo.TaskRepo;
import com.example.TaskManager.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    TaskRepo taskRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    CommentRepo commentRepo;

    public ResponseEntity<?> addComment(CommentDto commentDto) {

        Task task  = taskRepo.findById(commentDto.getTaskId()).orElseThrow(()->new RuntimeException("No Task Found"));
        UserModel user = userRepo.findById(commentDto.getUserId()).orElseThrow(()->new RuntimeException("No User Found"));
        Comments comment = new Comments();
        comment.setParentCommentId(commentDto.getParentCommentId());
        comment.setContents(commentDto.getContent());
        comment.setTask(task);
        comment.setUser(user);
        comment.setCreatedAt(new Date());

        return ResponseEntity.status(HttpStatus.CREATED).body(commentRepo.save(comment));
    }


    public ResponseEntity<?> getComments(Long taskId) {
        List<Comments> parentComments = commentRepo.findByTaskIdAndParentCommentIdIsNull(taskId);
        return ResponseEntity.ok(parentComments.stream().map(this::convertToDto).toList());
    }

    public CommentDto convertToDto(Comments comment){

        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setContent(comment.getContents());
        commentDto.setUserId(comment.getUser().getId());
        commentDto.setPostedBy(comment.getUser().getName());
        commentDto.setCreatedAt(comment.getCreatedAt());
        commentDto.setTaskId(comment.getTask().getId());
        commentDto.setParentCommentId(comment.getParentCommentId());

        List<Comments> replies = commentRepo.findByParentCommentId(comment.getId());

        List<CommentDto> replyDto = replies.stream().map(this::convertToDto).toList();
        commentDto.setReplies(replyDto);

        return commentDto;
    }
}
