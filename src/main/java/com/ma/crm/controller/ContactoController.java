package com.ma.crm.controller;

import javax.validation.Valid;

import com.ma.common.model.Lista;
import com.ma.crm.model.Contacto;
import com.ma.crm.model.ContactoDTO;
import com.ma.crm.model.ContactoItem;
import com.ma.crm.service.ContactoService;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * ContactoController
 */
@RestController
@RequestMapping("/contactos")
public class ContactoController {

  private ContactoService contactoService;
  private ModelMapper modelMapper;
  

  public ContactoController(ContactoService contactoService, ModelMapper modelMapper){
    this.contactoService = contactoService;
    this.modelMapper = modelMapper;
  }

  @GetMapping
  public Lista<ContactoItem> listar(
    @RequestParam(defaultValue = "", value="q", required = false) String busqueda, 
    @RequestParam(defaultValue = "0", value="page", required = false) int pagina, 
    @RequestParam(defaultValue = "10", value="limit", required = false) int limite) {
    return contactoService.listar(busqueda, pagina, limite);
  }

  @GetMapping("/{id}")
  public Contacto consultar(@PathVariable("id") long id){
    return contactoService.consultar(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Contacto crear(@Valid @RequestBody ContactoDTO contactoDTO) {
    Contacto contacto = modelMapper.map(contactoDTO, Contacto.class);
    return contactoService.crear(contacto);
  }

}