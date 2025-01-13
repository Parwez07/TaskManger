package com.example.TaskManager.Repo;

import com.example.TaskManager.Models.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepo extends JpaRepository<Comments,Long> {
    List<Comments> findByTaskIdAndParentCommentIdIsNull(Long taskId);
    List<Comments> findByParentCommentId(Long id);
}
