package com.ma.crm.model;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.Test;

/**
 * ContactoTest
 */
public class ContactoTest {

  private Validator validator;

  @Before
  public void setup() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  public void no_deber_dar_error() {
    Contacto contacto = ejemplo();

    Set<ConstraintViolation<Contacto>> errores = validator.validate(contacto);
    assertTrue(errores.isEmpty());
  }

  @Test
  public void no_deberia_dar_error_si_solo_tenemos_el_nombre_del_contacto() {
    Contacto contacto = new Contacto.Builder().nombre("Cosme Fulanito").build();
    Set<ConstraintViolation<Contacto>> errores = validator.validate(contacto);
    assertTrue(errores.isEmpty());
  }

  @Test
  public void deberia_dar_error_si_el_nombre_del_contacto_es_nulo() {
    Contacto contacto = ejemplo();
    contacto.setNombre(null);
    Set<ConstraintViolation<Contacto>> errores = validator.validate(contacto);
    ConstraintViolation<Contacto> error = errores.iterator().next();
    assertThat(error.getPropertyPath().toString(), containsString("nombre"));
    assertThat(error.getMessage(), containsString("no puede estar vacío"));
  }

  private Contacto ejemplo() {
    return new Contacto.Builder()
    .id(1232L)
    .nombre("Tzvetan Todorov")
    .nacimiento(LocalDate.of(1969, 7, 20))
    .email("tzvetan@email.com")
    .telefono("0312-4171812")
    .direccion("Siembre viva 3232")
    .localidad(new Localidad(1233L, "CABA", "123", "CABA"))
    .build();
  }
  
}