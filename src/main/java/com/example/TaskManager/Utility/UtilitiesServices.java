package com.example.TaskManager.Utility;

import com.example.TaskManager.Models.Dto.TaskDto;
import com.example.TaskManager.Models.Task;
import com.example.TaskManager.Models.UserModel;

public class UtilitiesServices {

    public static TaskDto mapToDto(Task task){
        UserModel user = task.getUser();
        return  new TaskDto(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                task.getPriority(),
                task.getTaskStatus(),
                user.getName(),
                user.getId()
        );
    }
}
