package com.lucas.tasktracker.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalHandlerException {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorMessage> handleNotFound(NotFoundException ex) {
        String message = ex.getMessage();
        String code = ex.getCode();

        ErrorMessage errorMessage = new ErrorMessage(code, message);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorMessage> handleBadArgument(BadArgumentException ex) {
        String message = ex.getMessage();
        String code = ex.getCode();

        ErrorMessage errorMessage = new ErrorMessage(code, message);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

}
