package com.example.TaskManager.Controller.Employee;

import com.example.TaskManager.Models.Dto.TaskDto;
import com.example.TaskManager.Models.UserModel;
import com.example.TaskManager.Services.Task.TaskServices;
import com.example.TaskManager.Services.Users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    UserService userService;

    @Autowired
    TaskServices taskServices;



    @GetMapping("/tasks")
    public ResponseEntity<?> getAllTasks(){

        UserModel currUser = userService.getCurrentLoggedIn();
        if (currUser==null)
            return ResponseEntity.notFound().build();


        List<TaskDto> employeeTask = taskServices.getEmployeeTask(currUser.getId());
        return ResponseEntity.ok(employeeTask);
    }

    @PutMapping("/updateTask/{id}")
    public ResponseEntity<?> updateTask(@RequestBody TaskDto task,@PathVariable Long id){

        if(task==null || id==null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        return taskServices.updateTask(task,id);
    }

}
