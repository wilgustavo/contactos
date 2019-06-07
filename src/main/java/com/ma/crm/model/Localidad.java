
package com.ma.crm.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Localidad
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Localidad {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @NotBlank
  private String nombre;
  @NotBlank
  @Size(max = 8, message = "codigo postal excede {max} caracteres")
  private String codigoPostal;
  private String provincia;

  public void actualizar(Localidad localidad) {
    nombre = localidad.nombre;
    codigoPostal = localidad.codigoPostal;
    provincia = localidad.provincia;
  }
  
}