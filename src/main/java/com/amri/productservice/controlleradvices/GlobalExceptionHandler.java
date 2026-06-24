package com.amri.productservice.controlleradvices;

import com.amri.productservice.exceptions.ProductNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<String> handleProductNotException(ProductNotFoundException exception){
        return  new ResponseEntity<>(
          exception.getMessage(), HttpStatus.BAD_REQUEST
        );
    }
}
