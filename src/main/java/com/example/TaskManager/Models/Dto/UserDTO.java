package com.example.TaskManager.Models.Dto;

import com.example.TaskManager.Enum.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {  // DTO:- data transfer object
    private  long id;
    private String email;
    private String name;
    private UserRole userRole;
}
