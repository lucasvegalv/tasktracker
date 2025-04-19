package com.lucas.tasktracker.dtos;

import com.lucas.tasktracker.entities.ProjectEntity;
import com.lucas.tasktracker.enumerations.JobsEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;
    private String username;
    private String lastname;
    private JobsEnum job;
}
