package com.example.TaskManager.Controller.Admin;

import com.example.TaskManager.Enum.UserRole;
import com.example.TaskManager.Models.Dto.TaskDto;
import com.example.TaskManager.Models.Task;
import com.example.TaskManager.Models.UserModel;
import com.example.TaskManager.Services.Task.TaskServices;
import com.example.TaskManager.Services.Users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    UserService service;
    @Autowired
    TaskServices taskServices;

    @PostMapping("/addAdmin")
    public ResponseEntity<?> registerAdmin(@RequestBody UserModel admin, Authentication authentication) {

        if (!authentication.getAuthorities().contains(new SimpleGrantedAuthority(UserRole.ADMIN.name())))
            return new ResponseEntity<>("Bhaag bsdk yaha se.. ", HttpStatus.FORBIDDEN);

        if (admin.getUserRole() == null)
            admin.setUserRole(UserRole.ADMIN);
        ResponseEntity<?> responseEntity = service.registerUser(admin);
        return new ResponseEntity<>(responseEntity.getBody(), responseEntity.getStatusCode());
    }

    @GetMapping("/getUsers")
    public ResponseEntity<?> getUsers() {
        return service.getUsers();
    }

    @GetMapping("/allTask")
    public ResponseEntity<?> getAllTask() {
        return new ResponseEntity<>(taskServices.getAllTask(), HttpStatus.OK);
    }

    @PostMapping("/addTask")
    public ResponseEntity<?> addTask(@RequestBody TaskDto task){
        if(task==null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        ResponseEntity<?> responseEntity = taskServices.addTask(task);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

    @DeleteMapping("/task/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable long id){
        return taskServices.deleteTask(id);
    }

    @GetMapping("/task/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable long id){
        return taskServices.getTaskById(id);
    }

    @PutMapping("/updateTask/{id}")
    public ResponseEntity<?> updateTask(@RequestBody TaskDto task,@PathVariable Long id){

        return taskServices.updateTask(task,id);
    }

    @GetMapping("/searchTask")
    public ResponseEntity<?> searchTask
            (@RequestParam(required = false) String keywords,@RequestParam(required = false,value = "id")Long id){
        if(id==null && keywords==null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        ResponseEntity<?> taskList = taskServices.search(keywords,id);
        return ResponseEntity.ok(taskList.getBody());
    }

    @GetMapping("/task/employee/{employeeId}")
    public ResponseEntity<?> getEmployeeTasks(@PathVariable("employeeId") Long id){

        List<TaskDto> tasks = taskServices.getEmployeeTask(id);
        if(tasks.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/adminTask")
    public ResponseEntity<?> getAdminTask(){
        UserModel currUser = service.getCurrentLoggedIn();
        if (currUser==null)
            return ResponseEntity.notFound().build();
        List<TaskDto> employeeTask = taskServices.getEmployeeTask(currUser.getId());
        return ResponseEntity.ok(employeeTask);
    }
}
