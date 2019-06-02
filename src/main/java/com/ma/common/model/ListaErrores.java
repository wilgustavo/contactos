
package com.ma.common.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ListaErrores
 */
@JsonInclude(Include.NON_NULL)
@Data
@NoArgsConstructor
public class ListaErrores {

  private List<Error> errores;
  private String mensaje;

  public ListaErrores(String mensaje) {
    this.mensaje = mensaje;
  }

  public void addError(String campo, String texto) {
    if (errores == null) {
      errores = new ArrayList<>();
    }
    errores.add(new Error(campo, texto));
  }

}