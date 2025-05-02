package com.lucas.tasktracker.exceptions;

import lombok.Getter;

@Getter
public abstract class CustomRuntimeException extends RuntimeException {

    private final String code;
    private final String message;

    public CustomRuntimeException(String code, String message) {
        super(message);
        this.message = message;
        this.code = code;
    }
}
