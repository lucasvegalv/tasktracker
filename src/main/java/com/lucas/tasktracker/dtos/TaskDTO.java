package com.lucas.tasktracker.dtos;

import com.lucas.tasktracker.entities.TaskListEntity;
import com.lucas.tasktracker.enumerations.PriorityEnum;
import com.lucas.tasktracker.enumerations.TaskStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {

    private String title;
    private String description;
    private Date dueDate;
    private PriorityEnum priority;
    private TaskStatusEnum status;
    private TaskListEntity taskList;

}
