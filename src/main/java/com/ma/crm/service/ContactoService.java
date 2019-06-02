package com.ma.crm.service;

import com.ma.common.model.Lista;
import com.ma.crm.model.Contacto;
import com.ma.crm.model.ContactoItem;

/**
 * ContactoService
 */
public interface ContactoService {

  Lista<ContactoItem> listar(String busqueda, int pagina, int limite);
  Contacto consultar(long id);
  Contacto crear(Contacto contacto);
}