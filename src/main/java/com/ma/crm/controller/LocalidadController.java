package com.ma.crm.controller;

import javax.validation.Valid;

import com.ma.common.model.Lista;
import com.ma.crm.model.Localidad;
import com.ma.crm.service.LocalidadService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * LocalidadController
 */
@RestController
@RequestMapping("/localidades")
public class LocalidadController {

  private LocalidadService localidadService;

  public LocalidadController(LocalidadService localidadService) {
    this.localidadService = localidadService;
  }

  @GetMapping
  public Lista<Localidad> listar(
    @RequestParam(value="q", required = true) String busqueda, 
    @RequestParam(defaultValue = "0", value="page", required = false) int pagina, 
    @RequestParam(defaultValue = "10", value="limit", required = false) int limite) {
    return localidadService.listar(busqueda, pagina, limite);
  }
  
  @GetMapping("/{id}")
  public Localidad consultar(@PathVariable("id") long id) {
    return localidadService.consultar(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Localidad crear(@Valid @RequestBody Localidad localidad) {
    return localidadService.crear(localidad);
  }

  @PutMapping("/{id}")
  public Localidad guardar(@PathVariable("id") long id, @Valid @RequestBody Localidad localidad) {
    localidad.setId(id);
    return localidadService.guardar(localidad);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void borrar(@PathVariable("id") long id) {
    localidadService.borrar(id);
  }
  
}