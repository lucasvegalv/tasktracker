package com.lucas.tasktracker.dtos.requests;

import com.lucas.tasktracker.enumerations.JobsEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestUserDTO {
    @NotBlank(message = "El nombre de usuario es obligatorio")
    @Size(max = 100)
    private String username;

    @NotBlank(message = "El nombre de usuario es obligatorio")
    @Size(max = 100)
    private String lastname;

    private JobsEnum job;
}
