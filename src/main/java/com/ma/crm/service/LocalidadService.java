package com.ma.crm.service;

import com.ma.common.model.Lista;
import com.ma.crm.model.Localidad;

/**
 * LocalidadService
 */
public interface LocalidadService {
  
  Lista<Localidad> listar(String busqueda, int pagina, int limite);
  Localidad consultar(long id);
  Localidad crear(Localidad localidad);
  Localidad guardar(Localidad localidad);
  void borrar(long id);

}