package com.lucas.tasktracker.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
public class BadArgumentException extends CustomRuntimeException {
    public BadArgumentException(String code, String message) {
        super(code, message);
    }
}
