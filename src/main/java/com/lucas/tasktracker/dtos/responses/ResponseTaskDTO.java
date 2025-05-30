package com.lucas.tasktracker.dtos.responses;

import com.lucas.tasktracker.enumerations.PriorityEnum;
import com.lucas.tasktracker.enumerations.TaskStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseTaskDTO {

    private Long taskId;
    private String title;
    private String description;
    private Date dueDate;
    private PriorityEnum priority;
    private TaskStatusEnum status;
    private SimpleResponseTaskListDTO taskList; // Evitamos dependencia circular al no usar ResponseTaskListDTO y por lo tanto usar TaskListMapper en TaskMapper

}
