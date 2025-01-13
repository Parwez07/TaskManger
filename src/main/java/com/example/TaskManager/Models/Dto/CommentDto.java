package com.example.TaskManager.Models.Dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    private Long id;
    private String content;
    private Date createdAt;
    private String postedBy;
    private Long parentCommentId;
    private Long taskId;
    private Long userId;

    private List<CommentDto> replies;
}
