package com.lucas.tasktracker.dtos;

import com.lucas.tasktracker.entities.ProjectEntity;
import com.lucas.tasktracker.entities.TaskEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskListDTO {

    private Long id;
    private String name;
    private String description;
    private Set<TaskEntity> tasks;
    private ProjectEntity project;

}
