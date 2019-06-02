package com.ma.common.model;

import java.util.List;

import org.springframework.data.domain.Page;

import lombok.Data;

/**
 * Lista
 */
@Data
public class Lista<T> {

  private List<T> datos;
  private int pagina;
  private int cantidad;
  private long total;
  
  public static <C>Lista<C> of(Page<C> page) {
    Lista<C> lista = new Lista<>();
    lista.datos = page.getContent();
    lista.pagina = page.getNumber();
    lista.cantidad = lista.datos.size();
    lista.total = page.getTotalElements();
    return lista;
  } 
}