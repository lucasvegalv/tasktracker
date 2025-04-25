package com.lucas.tasktracker.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseTaskListDTO {

    private Long taskListId;
    private String name;
    private String description;
    private Set<ResponseTaskDTO> tasks;
}
