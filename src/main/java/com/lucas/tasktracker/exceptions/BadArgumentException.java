package com.lucas.tasktracker.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@Schema(description = "Excepción para manejar errores de valores de entrada invalidos")
public class BadArgumentException extends CustomRuntimeException {
    public BadArgumentException(String code, String message) {
        super(code, message);
    }
}
