package com.ma.common.advice;

import com.ma.common.model.ListaErrores;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * ValidationAdvice
 */
@ControllerAdvice
public class ValidationAdvice {

	@ResponseBody
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public ListaErrores handleValidationError(MethodArgumentNotValidException ex) {
    BindingResult result = ex.getBindingResult();
    ListaErrores errores = new ListaErrores();
    errores.setMensaje("Errores de validación");
    for(FieldError error: result.getFieldErrors()) {
      errores.addError(error.getField(), error.getDefaultMessage());
    }
    return errores;
  }
}