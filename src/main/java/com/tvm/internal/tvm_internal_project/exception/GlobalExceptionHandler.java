package com.tvm.internal.tvm_internal_project.exception;

import com.tvm.internal.tvm_internal_project.response.ResponseStructure;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Hidden
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<ResponseStructure<String>> handleNotFound(ResourceNotFound exception) {
        ResponseStructure<String> structure = new ResponseStructure<>();
        structure.setBody(null);
        structure.setMessage(exception.getMessage());
        structure.setStatusCode(HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(structure, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<ResponseStructure<String>> handleDuplicate(DuplicateException exception) {
        ResponseStructure<String> structure = new ResponseStructure<>();
        structure.setBody(null);
        structure.setMessage(exception.getMessage());
        structure.setStatusCode(HttpStatus.CONFLICT.value());
        return new ResponseEntity<>(structure, HttpStatus.CONFLICT);
    }

    @ExceptionHandler({NumberFormatException.class, IllegalArgumentException.class})
    public ResponseEntity<ResponseStructure<String>> handleBadRequest(Exception exception) {
        ResponseStructure<String> structure = new ResponseStructure<>();
        structure.setBody(null);
        structure.setMessage("Invalid request format");
        structure.setStatusCode(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(structure, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ResponseStructure<String>> handleForbidden(ForbiddenException exception) {
        ResponseStructure<String> structure = new ResponseStructure<>();
        structure.setBody(null);
        structure.setMessage(exception.getMessage());
        structure.setStatusCode(HttpStatus.FORBIDDEN.value());
        return new ResponseEntity<>(structure, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseStructure<String>> handleGeneralException(Exception exception) {
        ResponseStructure<String> structure = new ResponseStructure<>();
        structure.setBody(null);
        structure.setMessage(exception.getMessage());
        structure.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(structure, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
