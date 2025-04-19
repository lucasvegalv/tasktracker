package com.lucas.tasktracker.dtos;

import com.lucas.tasktracker.entities.TaskListEntity;
import com.lucas.tasktracker.enumerations.PriorityEnum;
import com.lucas.tasktracker.enumerations.TaskStatusEnum;

import java.util.Date;

public class TaskDTO {

    private String title;
    private String description;
    private Date dueDate;
    private PriorityEnum priority;
    private TaskStatusEnum status;
    private TaskListEntity taskList;

}
