package com.lucas.tasktracker.dtos;

import com.lucas.tasktracker.entities.ProjectEntity;
import com.lucas.tasktracker.enumerations.JobsEnum;
import java.util.Set;

public class UserDTO {

    private String username;
    private String lastname;
    private JobsEnum job;
    private Set<ProjectEntity> projects;

}
