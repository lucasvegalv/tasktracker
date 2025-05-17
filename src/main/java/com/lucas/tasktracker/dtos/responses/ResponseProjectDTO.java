package com.lucas.tasktracker.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseProjectDTO {

    private Long projectId;
    private String title;
    private Set<ResponseTaskListDTO> taskLists;
    private Set<ResponseUserDTO> members;
}
