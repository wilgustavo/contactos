package com.ma.crm.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Contacto
 */
@Data
@NoArgsConstructor
@Entity
public class Contacto {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

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

  @ManyToOne()
  @JoinColumn(name = "localidad")
  private Localidad localidad;

  public static class Builder {
    private Long id;
    private String nombre;
    private LocalDate nacimiento;
    private String email;
    private String telefono;
    private String direccion;
    private Localidad localidad;

    public Builder id(Long id) {
      this.id = id;
      return this;
    }

    public Builder nombre(String nombre) {
      this.nombre = nombre;
      return this;
    }

    public Builder nacimiento(LocalDate nacimiento) {
      this.nacimiento = nacimiento;
      return this;
    }

    public Builder email(String email) {
      this.email = email;
      return this;
    }

    public Builder telefono(String telefono) {
      this.telefono = telefono;
      return this;
    }

    public Builder direccion(String direccion) {
      this.direccion = direccion;
      return this;
    }

    public Builder localidad(Localidad localidad) {
      this.localidad = localidad;
      return this;
    }

    public Contacto build() {
      return new Contacto(this);
    }

  }

  private Contacto(Builder builder) {
    id = builder.id;
    nombre = builder.nombre;
    nacimiento = builder.nacimiento;
    email = builder.email;
    telefono = builder.telefono;
    direccion = builder.direccion;
    localidad = builder.localidad;
  }
  
}