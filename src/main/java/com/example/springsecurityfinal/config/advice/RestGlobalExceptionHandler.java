package com.example.springsecurityfinal.config.advice;

import com.example.springsecurityfinal.exception.MemberAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestGlobalExceptionHandler {
    @ExceptionHandler(value = MemberAlreadyExistsException.class)
    public ResponseEntity<String> handleException(MemberAlreadyExistsException e) {
        return new ResponseEntity<>("이미 존재하는 id 입니다.", HttpStatus.CONFLICT);
    }
}
