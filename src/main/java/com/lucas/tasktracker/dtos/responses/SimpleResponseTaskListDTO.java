package com.lucas.tasktracker.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleResponseTaskListDTO {
    private Long taskListId;
    private String name;
}
