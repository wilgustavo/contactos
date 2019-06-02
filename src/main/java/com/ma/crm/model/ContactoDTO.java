package com.ma.crm.model;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

/**
 * ContactoDTO
 */
@Data
public class ContactoDTO {

  @NotBlank
  @Size(max = 100)
  private String nombre;

  @Past
  private LocalDate nacimiento;

  @Email
  @Size(max = 120)
  private String email;

  @Pattern(regexp = "[0-9\\.\\s\\(\\-\\)]+")
  @Size(max = 20)
  private String telefono;

  private String direccion;

  private Long localidadId;
  
}