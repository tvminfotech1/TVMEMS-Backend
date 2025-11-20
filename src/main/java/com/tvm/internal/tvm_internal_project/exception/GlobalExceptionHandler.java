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


    @ExceptionHandler(PersonalNotFoundException.class)
    public ResponseEntity<ResponseStructure<String>> handleUNFE(PersonalNotFoundException exception) {
        ResponseStructure<String> structure = new ResponseStructure<>();
        structure.setBody("ID Not Found");
        structure.setMessage(exception.getMessage());
        structure.setStatusCode(HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<ResponseStructure<String>>(structure, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoTaskFoundException.class)
    public ResponseEntity<ResponseStructure<String>> handle(NoTaskFoundException exception) {
        ResponseStructure<String> structure = new ResponseStructure<>();
        structure.setBody("ID Not Found");
        structure.setMessage(exception.getMessage());
        structure.setStatusCode(HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<ResponseStructure<String>>(structure, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TimeSheetNotFoundException.class)
    public ResponseEntity<ResponseStructure<String>> handle(TimeSheetNotFoundException exception) {
        ResponseStructure<String> structure = new ResponseStructure<>();
        structure.setBody("ID Not Found");
        structure.setMessage(exception.getMessage());
        structure.setStatusCode(HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<ResponseStructure<String>>(structure, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<ResponseStructure<String>> handleUNFE(EmployeeNotFoundException exception) {
        ResponseStructure<String> structure = new ResponseStructure<>();
        structure.setBody(exception.getMessage());
        structure.setMessage(exception.getMessage());
        structure.setStatusCode(HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<ResponseStructure<String>>(structure, HttpStatus.NOT_FOUND);
    }

}
