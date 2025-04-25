package com.lucas.tasktracker.dtos.requests;

import com.lucas.tasktracker.enumerations.PriorityEnum;
import com.lucas.tasktracker.enumerations.TaskStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestTaskDTO {
    private String title;
    private String description;
    private Date dueDate;
    private PriorityEnum priority;
    private TaskStatusEnum status;
}
