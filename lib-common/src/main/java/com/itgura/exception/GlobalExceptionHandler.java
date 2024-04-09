package com.itgura.exception;

import com.itgura.dto.AppResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<AppResponse> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> AppResponse.error(null, fieldError.getDefaultMessage(), "Bad Request", "400", ""))
                .collect(Collectors.toList());

        return ResponseEntity.badRequest().body(errors.isEmpty() ? "Error processing request" : errors.get(0));
    }
}
