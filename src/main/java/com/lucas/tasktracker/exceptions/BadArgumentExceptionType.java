package com.lucas.tasktracker.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BadArgumentExceptionType {

    INVALID_PROJECT_NAME("INVALID_PROJECT_NAME", "Project name must not be empty or exceed 100 characters."),
    INVALID_TASK_NAME("INVALID_TASK_NAME", "Task name must not be empty or exceed 50 characters."),
    DUPLICATE_TASK_NAME("DUPLICATE_TASK_NAME", "A task with this name already exists in the list."),
    MISSING_TASKLIST_ID("MISSING_TASKLIST_ID", "You must provide a task list ID to assign the task to."),
    INVALID_USER_ID("INVALID_USER_ID", "Provided user ID is invalid or null."),
    INVALID_DATE_FORMAT("INVALID_DATE_FORMAT", "Due date format is invalid. Expected format: yyyy-MM-dd."),
    TASKLIST_ALREADY_HAS_TASK("TASKLIST_ALREADY_HAS_TASK", "The task is already assigned to this task list."),
    UNAUTHORIZED_OPERATION("UNAUTHORIZED_OPERATION", "You are not allowed to perform this operation."),
    PROJECT_ALREADY_EXISTS("PROJECT_ALREADY_EXISTS", "A project with the same name already exists."),
    TASK_ALREADY_COMPLETED("TASK_ALREADY_COMPLETED", "This task is already marked as completed."),
    USER_ALREADY_MEMBER("USER_ALREADY_MEMBER", "This user is already marked as member.");

    private final String code;
    private final String message;

    public BadArgumentException getException() {
        return new BadArgumentException(code, message);
    }
}
