package com.lucas.tasktracker.dtos.responses;

import com.lucas.tasktracker.dtos.TaskListDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseProjectDTO {

    private Long projectId;
    private String title;
    private Set<TaskListDTO> taskLists;
    private Set<ResponseUserDTO> members;
}
