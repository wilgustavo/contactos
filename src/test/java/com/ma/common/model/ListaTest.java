package com.ma.common.model;

import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;

import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

/**
 * ListaTest
 */
public class ListaTest {

  @Test
  public void deber_crear_crearse_una_lista_con_una_pagina() {
    List<String> contenido = Arrays.asList("Uno", "Dos", "Tres");
    Page<String> pagina = new PageImpl<>(contenido, PageRequest.of(2, 11), 99L);
    Lista<String> lista = Lista.of(pagina);

    assertThat(lista.getDatos(), contains("Uno", "Dos", "Tres"));
    assertThat(lista.getCantidad(), is(3));
    assertThat(lista.getPagina(), is(2));
    assertThat(lista.getTotal(), is(99L));
  }
}