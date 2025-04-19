package com.lucas.tasktracker.dtos;

import com.lucas.tasktracker.entities.TaskListEntity;
import com.lucas.tasktracker.entities.UserEntity;

import java.util.Set;

public class ProjectDTO {

    private String title;
    private Set<TaskListEntity> taskLists;
    private Set<UserEntity> members;

}
