package com.ma.crm.repository;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import com.ma.crm.model.ContactoItem;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * ContactoRepositoryIntegrationTest
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ContactoRepositoryIntegrationTest {

  @Autowired
  private ContactoRespository contactoRespository;

  @Test
  @Sql({"/sql/localidad.sql", "/sql/contacto.sql"}) 
  public void deberia_retornar_una_lista_de_items() {
    PageRequest pr = PageRequest.of(0, 10, Sort.by("nombre"));
    Page<ContactoItem> lista = contactoRespository.findByNombreContainingIgnoreCase("AR", pr);
    assertThat(lista.getNumberOfElements(), is(3));
  }
  
}