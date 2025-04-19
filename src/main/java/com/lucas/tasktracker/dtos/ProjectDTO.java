package com.lucas.tasktracker.dtos;

import com.lucas.tasktracker.entities.TaskListEntity;
import com.lucas.tasktracker.entities.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDTO {

    private String title;
    private Set<TaskListEntity> taskLists;
    private Set<UserEntity> members;

}
