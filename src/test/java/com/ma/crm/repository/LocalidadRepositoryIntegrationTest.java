package com.ma.crm.repository;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import javax.validation.ConstraintViolationException;

import com.ma.crm.model.Localidad;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * LocalidadRepositoryIntegrationTest
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LocalidadRepositoryIntegrationTest {

  @Autowired
  private LocalidadRepository localidadRepository;

  @Test
  @Sql("/sql/localidad.sql") 
  @SuppressWarnings("unchecked")
  public void deberia_listar_localidades_por_nombre() {
    Page<Localidad> pagina = localidadRepository.findByNombreContainingIgnoreCase("corone", PageRequest.of(0, 10));
    
    assertThat(pagina.getNumberOfElements(), is(2));
    assertThat(pagina.getContent(), hasItems(
      Matchers.<Localidad>hasProperty("nombre", is("CORONEL PRINGLES")) ,
      Matchers.<Localidad>hasProperty("nombre", is("CORONEL SUAREZ"))
    ));
  }

  @Test(expected = ConstraintViolationException.class)
  @Sql("/sql/localidad.sql") 
  public void deberia_dar_error_al_guardar_localidad_no_valida() {
    Localidad localidad = new Localidad(null, null, "12345678", "prueba");
    localidadRepository.save(localidad);
  }

}