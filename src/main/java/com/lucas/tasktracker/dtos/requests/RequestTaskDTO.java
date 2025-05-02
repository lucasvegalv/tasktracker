package com.lucas.tasktracker.dtos.requests;

import com.lucas.tasktracker.enumerations.PriorityEnum;
import com.lucas.tasktracker.enumerations.TaskStatusEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestTaskDTO {
    @NotBlank(message = "El titulo es obligatorio")
    @Size(max = 255)
    private String title;

    private String description;

    private Date dueDate;

    private PriorityEnum priority;

    private TaskStatusEnum status;
}
