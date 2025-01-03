package com.example.TaskManager.Models.Dto;

import com.example.TaskManager.Enum.TaskPriority;
import com.example.TaskManager.Enum.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {

    private long taskId;
    private String taskTitle;
    private String taskDescription;
    private Date taskDueDate;
    private TaskPriority taskPriority;
    private TaskStatus taskStatus;
    private String employeeName;
    private Long employeeId;

}
