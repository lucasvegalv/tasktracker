package com.lucas.tasktracker.exceptions;

import lombok.Getter;
import org.hibernate.annotations.IdGeneratorType;

@Getter
public class NotFoundException extends CustomRuntimeException {
    public NotFoundException(String code, String message) {
        super(code, message);
    }
}
