package com.ma.crm.service;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import com.ma.common.exception.NotFoundException;
import com.ma.common.model.Lista;
import com.ma.crm.model.Localidad;
import com.ma.crm.repository.LocalidadRepository;

import org.junit.Before;
import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

/**
 * LocalidadServiceTest
 */
public class LocalidadServiceTest {

  private LocalidadRepository localidadRepository;
  private LocalidadService localidadService;

  @Before
  public void init() {
    localidadRepository = mock(LocalidadRepository.class);
    localidadService = new LocalidadServiceImpl(localidadRepository);
  }

  @Test
  public void deberia_retornar_una_lista_vacia_cuando_no_encuentra_localidades() {
    Page<Localidad> pagina = new PageImpl<>(new ArrayList<Localidad>());
    when(localidadRepository.findByNombreContainingIgnoreCase(any(), any())).thenReturn(pagina);
    Lista<Localidad> lista = localidadService.listar("ZZZ", 0, 10);
    assertThat(lista.getDatos(), empty());
    assertThat(lista.getCantidad(), is(0));
  }

  @Test
  @SuppressWarnings("unchecked")
  public void deberia_retornar_una_lista_de_localidades() {
    Localidad[] contenido = { 
      new Localidad(1L, "Bs.As. (CABA)", "1001", "CABA"), 
      new Localidad(80L, "Caballito", "7002", "CABA"), 
      new Localidad(432L, "CABA", "1005", "CABA")
    };

    Page<Localidad> pagina = new PageImpl<>(Arrays.asList(contenido), PageRequest.of(1, 3), 42);
    when(localidadRepository.findByNombreContainingIgnoreCase("CABA", PageRequest.of(1, 3))).thenReturn(pagina);
    Lista<Localidad> lista = localidadService.listar("CABA", 1, 3);
    assertThat(lista.getDatos(), not(empty()));
    assertThat(lista.getCantidad(), is(3));
    assertThat(lista.getTotal(), is(42L));
    assertThat(lista.getDatos(), hasItems(
      hasProperty("nombre", is("CABA")) ,
      hasProperty("nombre", is("Caballito")),
      hasProperty("codigoPostal", is("7002"))
    ));

  }

  @Test(expected = NotFoundException.class)
  public void deberia_dar_error_si_no_existe_localidad() {
    when(localidadRepository.findById(100L)).thenReturn(Optional.empty());
    localidadService.consultar(100L);
  }

  @Test
  public void deberia_retonar_localidad_al_consultar() {
    long id = 5L;
    when(localidadRepository.findById(id)).thenReturn(Optional.of(new Localidad(id, "COMODORO RIVADAVIA", "7632", "Chubut")));
    Localidad localidad = localidadService.consultar(id);
    assertThat(localidad.getNombre(), is("COMODORO RIVADAVIA"));
    assertThat(localidad.getProvincia(), is("Chubut"));
    assertThat(localidad.getCodigoPostal(), is("7632"));
  }

  @Test
  public void deberia_crear_localidad() {
    when(localidadRepository.save(any(Localidad.class))).thenReturn(new Localidad(321L, "CABA", "1001", "CABA"));
    Localidad localidad = localidadService.crear(new Localidad(null, "CABA", "1001", "CABA"));
    assertThat(localidad.getId(), is(321L));
    assertThat(localidad.getNombre(), is("CABA"));
    assertThat(localidad.getProvincia(), is("CABA"));
    assertThat(localidad.getCodigoPostal(), is("1001"));
  }

  @Test(expected = NotFoundException.class)
  public void deberia_dar_error_al_grabar_una_localidad_que_no_existe() {
    Localidad localidad = new Localidad(8L, "Rawson", "1111", "CHUBUT");
    when(localidadRepository.existsById(8L)).thenReturn(false);
    localidadService.guardar(localidad);
  }

  @Test
  public void deberia_grabar_una_localidad() {
    Localidad localidad = new Localidad(8L, "Rawson", "1111", "CHUBUT");
    when(localidadRepository.findById(8L)).thenReturn(Optional.of(localidad));
    Localidad resultado = localidadService.guardar(localidad);
    assertThat(resultado, is(notNullValue()));
  }

  @Test
  public void deberia_borrar_una_localidad() {
    when(localidadRepository.existsById(23L)).thenReturn(true);
    doNothing().when(localidadRepository).deleteById(any());
    localidadService.borrar(23L);
    verify(localidadRepository, times(1)).deleteById(23L);
  }

  @Test
  public void no_deberia_borrar_una_localidad_que_no_existe() {
    when(localidadRepository.existsById(23L)).thenReturn(false);
    doNothing().when(localidadRepository).deleteById(any());
    localidadService.borrar(23L);
    verify(localidadRepository, never()).deleteById(23L);
  }
  
}