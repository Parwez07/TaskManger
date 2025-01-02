package com.example.TaskManager.Services.Task;

import com.example.TaskManager.Enum.TaskStatus;
import com.example.TaskManager.Models.Dto.TaskDto;
import com.example.TaskManager.Models.Task;
import com.example.TaskManager.Models.UserModel;
import com.example.TaskManager.Repo.TaskRepo;
import com.example.TaskManager.Repo.UserRepo;

import com.example.TaskManager.Utility.UtilitiesServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TaskServices {

    @Autowired
    TaskRepo taskRepo;
    @Autowired
    UserRepo userRepo;

    public ResponseEntity<?> getAllTask() {

        List<TaskDto> taskDtoList = taskRepo.findAll().stream()
                .map(UtilitiesServices::mapToDto).toList();

        return new ResponseEntity<>(taskDtoList, HttpStatus.OK);
    }




    public ResponseEntity<?> addTask(TaskDto task) {
        UserModel user = userRepo.findById(task.getEmployeeId())
                                .orElseThrow(() -> new RuntimeException("User not found with ID: " + task.getEmployeeId()));

        Task t = new Task();
        t.setDescription(task.getTaskDescription());
        t.setTaskStatus(TaskStatus.INPROGRESS);
        t.setId(task.getTaskId());
        t.setTitle(task.getTaskTitle());
        t.setUser(user);
        t.setDueDate(task.getTaskDueDate());
        t.setPriority(task.getTaskPriority());
        Task save = taskRepo.save(t);
        return new ResponseEntity<>(save,HttpStatus.CREATED);
    }

    public ResponseEntity<?> deleteTask(long id) {
        Task task = taskRepo.findById(id)
                .orElseThrow(()->new RuntimeException("Task Not Found with this Id "+id));
        taskRepo.deleteById(id);
        return new ResponseEntity<>(task,HttpStatus.OK);
    }

    public ResponseEntity<?> updateTask(TaskDto task,long id) {
        UserModel userToAssign = userRepo.findById(task.getEmployeeId()).orElseThrow(()->new RuntimeException("Employee dose not exit with Assigned Employee Id"));
        Task oldTask = taskRepo.findById(id).orElse(new Task());
        oldTask.setDueDate(task.getTaskDueDate());
        oldTask.setDescription(task.getTaskDescription());
        oldTask.setTitle(task.getTaskTitle());
        oldTask.setUser(userToAssign);
        oldTask.setPriority(task.getTaskPriority());
        oldTask.setTaskStatus(task.getTaskStatus());

        return new ResponseEntity<>(taskRepo.save(oldTask),HttpStatus.ACCEPTED);
    }

    public ResponseEntity<?> getTaskById(long id) {
        Task task = taskRepo.findById(id).orElseThrow(()->new RuntimeException("No Task Exist by this Id "+id));
        return ResponseEntity.ok(UtilitiesServices.mapToDto(task));
    }

    public ResponseEntity<?> search(String keywords, Long id) {

        Set<TaskDto> result = new HashSet<>();
        if(id !=null){
             taskRepo.findById(id)
                    .map(UtilitiesServices::mapToDto)
                    .ifPresent(result::add);
        }

        // If keywords are provided, search for matching tasks and map them to DTOs
        if (keywords != null && !keywords.trim().isEmpty()) {
            List<TaskDto> list = taskRepo.findByTitleContainingOrDescriptionContaining(keywords, keywords)
                    .stream()
                    .map(UtilitiesServices::mapToDto)
                    .toList();
            result.addAll(list);
        }

        if(result.isEmpty())
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok(result);

    }

    public List<TaskDto> getEmployeeTask(Long id) {
        List<Task> task = taskRepo.findByUser_id(id);
        return task.stream().map(UtilitiesServices::mapToDto).toList();
    }
}
