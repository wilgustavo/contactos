package com.ma.crm.model;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.Test;

/**
 * LocalidadTest
 */
public class LocalidadTest {

  private Validator validator;

  @Before
  public void setup() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  public void no_deberia_dar_error() {
    Localidad localidad = new Localidad(1L, "Tandil", "7600", "Buenos Aires");

    Set<ConstraintViolation<Localidad>> errores = validator.validate(localidad);
    assertTrue(errores.isEmpty());
  }

  @Test
  public void deberia_dar_error_si_no_tiene_nombre() {
    Localidad localidad = new Localidad(1L, null, "7605", "Buenos Aires");

    Set<ConstraintViolation<Localidad>> errores = validator.validate(localidad);
    assertThat(errores.size(), is(1));

    ConstraintViolation<Localidad> error = errores.iterator().next();
    assertThat(error.getPropertyPath().toString(), containsString("nombre"));
    assertThat(error.getMessage(), containsString("no puede estar vacío"));
  }

  @Test
  public void deberia_dar_error_si_no_tiene_codigo_postal() {
    Localidad localidad = new Localidad(1L, "Tandil", "", "Buenos Aires");

    Set<ConstraintViolation<Localidad>> errores = validator.validate(localidad);
    assertThat(errores.size(), is(1));

    ConstraintViolation<Localidad> error = errores.iterator().next();
    assertThat(error.getPropertyPath().toString(), containsString("codigoPostal"));
    assertThat(error.getMessage(), containsString("no puede estar vacío"));
  }
  
  @Test
  public void deberia_dar_error_si_no_codigo_postal_supera_8_caracteres() {
    Localidad localidad = new Localidad(1L, "Tandil", "123456789", "Buenos Aires");

    Set<ConstraintViolation<Localidad>> errores = validator.validate(localidad);
    assertThat(errores.size(), is(1));

    ConstraintViolation<Localidad> error = errores.iterator().next();
    assertThat(error.getPropertyPath().toString(), containsString("codigoPostal"));
    assertThat(error.getMessage(), containsString("codigo postal excede 8 caracteres"));
  }

  @Test
  public void deberia_actualizar_localidad() {
    Localidad original = new Localidad(10L, "Cordoba", "5000", "CBA");
    Localidad cambio = new Localidad(10L, "Cba capital", "5001", "Cordoba");
    original.actualizar(cambio);
    assertThat(original, is(cambio));
  }
  
}