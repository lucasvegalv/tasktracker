package com.lucas.tasktracker.dtos.requests;

import com.lucas.tasktracker.enumerations.JobsEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestUserDTO {
    private String username;
    private String lastname;
    private JobsEnum job;
}
