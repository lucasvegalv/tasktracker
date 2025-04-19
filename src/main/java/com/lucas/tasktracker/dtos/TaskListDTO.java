package com.lucas.tasktracker.dtos;

import com.lucas.tasktracker.entities.ProjectEntity;
import com.lucas.tasktracker.entities.TaskEntity;
import jakarta.persistence.*;

import java.util.Set;

public class TaskListDTO {

    private String name;
    private String description;
    private Set<TaskEntity> tasks;
    private ProjectEntity project;

}
