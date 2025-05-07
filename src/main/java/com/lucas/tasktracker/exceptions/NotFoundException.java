package com.lucas.tasktracker.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.hibernate.annotations.IdGeneratorType;

@Getter
@Schema(description = "Excepción para manejar errores de recursos no encontrados")
public class NotFoundException extends CustomRuntimeException {
    public NotFoundException(String code, String message) {
        super(code, message);
    }
}
