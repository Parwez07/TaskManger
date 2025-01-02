package com.example.TaskManager.Repo;

import com.example.TaskManager.Models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepo extends JpaRepository<Task, Long> {

    List<Task> findByTitleContainingOrDescriptionContaining(String keywords, String keywords1);

    List<Task> findByUser_id(Long id);
}
