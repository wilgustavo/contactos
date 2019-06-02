package com.ma.crm.service;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.ma.common.exception.NotFoundException;
import com.ma.common.exception.ValidationException;
import com.ma.common.model.Lista;
import com.ma.crm.model.Contacto;
import com.ma.crm.model.ContactoItem;
import com.ma.crm.model.Localidad;
import com.ma.crm.repository.ContactoRespository;


import org.junit.Before;
import org.junit.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

/**
 * ContactoServiceTest
 */
public class ContactoServiceTest {

  private ContactoRespository contactoRespository;
  private LocalidadService localidadService;
  private ContactoService contactoService;

  @Before
  public void init(){
    contactoRespository = mock(ContactoRespository.class);
    localidadService = mock(LocalidadService.class);
    contactoService = new ContactoServiceImpl(contactoRespository, localidadService);
  }

  @Test
  @SuppressWarnings("unchecked")
  public void deberia_retornar_lista_de_contactos() {
    List<ContactoItem> contenido = Arrays.asList(
      new ContactoItem(101L, "Cosme Fulanito", "cosme@email.com", null), 
      new ContactoItem(68L, "Cosmo Kramer", "kramer@email.com", "55555540")
    );

    PageImpl<ContactoItem> page = new PageImpl<>(contenido, PageRequest.of(0, 10), 2);
    when(contactoRespository.findByNombreContainingIgnoreCase(any(), any())).thenReturn(page);
    Lista<ContactoItem> lista = contactoService.listar("COSM", 0, 10);
    assertThat(lista.getDatos(), contains(
      hasProperty("nombre", is("Cosme Fulanito")) ,
      hasProperty("nombre", is("Cosmo Kramer"))
    ));
  }
  
  @Test
  public void deberia_retornar_lista_vacia_si_no_encuentra_contactos() {
    PageImpl<ContactoItem> page = new PageImpl<>(new ArrayList<ContactoItem>(), PageRequest.of(0, 10), 0);
    when(contactoRespository.findByNombreContainingIgnoreCase(any(), any())).thenReturn(page);
    Lista<ContactoItem> lista = contactoService.listar("ZZZ", 0, 10);
    assertThat(lista.getDatos(), empty());
    assertThat(lista.getCantidad(), is(0));
  }

  @Test
  public void deberia_consultar_contacto() {
    when(contactoRespository.findById(222L)).thenReturn(Optional.of(ejemplo()));
    Contacto contacto = contactoService.consultar(222L);
    assertThat(contacto, is(not(nullValue())));    
    assertThat(contacto.getId(), is(222L));
    assertThat(contacto.getNombre(), is("Antonio Ochoa"));
  }

  @Test(expected = NotFoundException.class)
  public void deberia_dar_error_al_consultar_contacto_que_no_existe() {
    when(contactoRespository.findById(100L)).thenReturn(Optional.empty());
    contactoService.consultar(100L);
  }
  
  @Test
  public void deberia_crear_un_contacto() {
    when(contactoRespository.save(any())).thenReturn(ejemplo());
    when(localidadService.consultar(12202L)).thenReturn(new Localidad(12202L, "CABA", "1231", "CABA"));
    Contacto contacto = contactoService.crear(ejemplo());
    assertThat(contacto.getId(), is(222L));
    assertThat(contacto.getNombre(), is("Antonio Ochoa"));
    assertThat(contacto.getLocalidad(), is(not(nullValue())));
    assertThat(contacto.getLocalidad().getId(), is(12202L));
  }

  @Test(expected = ValidationException.class)
  public void deberia_dar_error_si_crea_contacto_con_localidad_que_no_existe() {
    when(contactoRespository.save(any())).thenReturn(ejemplo());
    when(localidadService.consultar(12202L)).thenThrow(new NotFoundException("No existe localidad"));
    contactoService.crear(ejemplo());
  }

  private Contacto ejemplo(){
    return new Contacto.Builder()
    .id(222L)
    .nombre("Antonio Ochoa")
    .nacimiento(LocalDate.of(1943, 9, 18))
    .email("ochoaaa@hotmail.com")
    .telefono("021-41202243")
    .direccion("Callao 32")
    .localidad(new Localidad(12202L, "CABA", "1231", "CABA"))
    .build();
  }

}