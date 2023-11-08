package com.example.zad01.exeception;

import com.example.zad01.exeception.exceptionsClass.CapybaraAgeIsToLowException;
import com.example.zad01.exeception.exceptionsClass.CapybaraAlreadyExistException;
import com.example.zad01.exeception.exceptionsClass.CapybaraNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CapybaraExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CapybaraNotExistException.class)
    protected ResponseEntity<String> capybaraIsNull(RuntimeException ex , WebRequest request){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CapybaraAlreadyExistException.class)
    protected ResponseEntity<String> capybaraExist(RuntimeException ex , WebRequest request){
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(CapybaraAgeIsToLowException.class)
    protected ResponseEntity<String> capybaraAgeIsToLow(RuntimeException ex, WebRequest request){
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
