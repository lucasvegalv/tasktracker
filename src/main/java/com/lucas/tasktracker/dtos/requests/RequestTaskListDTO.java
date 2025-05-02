package com.lucas.tasktracker.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestTaskListDTO {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 255)
    private String name;

    private String description;
}
