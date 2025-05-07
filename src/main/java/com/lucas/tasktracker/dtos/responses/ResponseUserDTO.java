package com.lucas.tasktracker.dtos.responses;

import com.lucas.tasktracker.enumerations.JobsEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseUserDTO {

    private Long userId;
    private String username;
    private String lastname;
    private JobsEnum job;
}
