package com.ma.common.exception;

import java.util.Set;

import javax.validation.ConstraintViolation;

import com.ma.common.model.ListaErrores;


/**
 * ValidationException
 */
public class ValidationException extends RuntimeException {

  private static final long serialVersionUID = -1904822202257406686L;

  private ListaErrores listaErrores;

  public ValidationException(Set<ConstraintViolation<Object>> violations) {
    listaErrores = new ListaErrores("Error de validaciones");
    violations.forEach((v) -> {
      listaErrores.addError(v.getPropertyPath().toString(), v.getMessage());
    });
  }

  public ValidationException(ListaErrores listaErrores) {
    this.listaErrores = listaErrores;
  }

  public ValidationException(String campo, String texto) {
    listaErrores = new ListaErrores("Error de validaciones");
    listaErrores.addError(campo, texto);
  }

  public ListaErrores getListaErrores() {
    return listaErrores;
  }
  
}