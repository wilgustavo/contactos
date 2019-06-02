package com.ma.common.advice;

import com.ma.common.exception.NotFoundException;
import com.ma.common.model.ListaErrores;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * NotFoundAdvice
 */
@ControllerAdvice
public class NotFoundAdvice {
  
  @ResponseBody
  @ExceptionHandler(NotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  ListaErrores handleNotFound(NotFoundException ex) {
    return new ListaErrores(ex.getMessage()); 
  }
}