package com.lucas.tasktracker.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleResponseProjectDTO {

    private Long projectId;
    private String title;

}
