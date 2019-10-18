package com.sgaraba.springmastering.controller;

import com.sgaraba.springmastering.bean.ExceptionResponse;
import com.sgaraba.springmastering.exception.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestController
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<ExceptionResponse> notFound(NotFoundException ex) {
        return new ResponseEntity<ExceptionResponse>(
                new ExceptionResponse(ex.getMessage(), "Any details you would want to add"),
                new HttpHeaders(),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionResponse> badRequest(Exception ex) {
        return new ResponseEntity<ExceptionResponse>(
                new ExceptionResponse(ex.getMessage(), "Upsss. Bad request."),
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST
        );
    }
}