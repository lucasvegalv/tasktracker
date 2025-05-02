package com.lucas.tasktracker.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;

@Getter
@AllArgsConstructor
public enum NotFoundExceptionType {
    PROJECT_NOT_FOUND("PROJECT_NOT_FOUND", "The project couldn't be found"),
    TASK_NOT_FOUND("TASK_NOT_FOUND", "The task couldn't be found"),
    TASKLIST_NOT_FOUND("TASKLIST_NOT_FOUND", "The task list couldn't be found"),
    USER_NOT_FOUND("USER_NOT_FOUND", "The user couldn't be found");

    private final String code;
    private final String message;

    public NotFoundException getException() {
        return new NotFoundException(code, message);
    }
}
