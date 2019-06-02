package com.ma.crm.service;

import com.ma.common.exception.NotFoundException;
import com.ma.common.model.Lista;
import com.ma.crm.model.Localidad;
import com.ma.crm.repository.LocalidadRepository;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * LocalidadServiceImpl
 */
@Service
public class LocalidadServiceImpl implements LocalidadService {

  private static final String MSG_NOT_FOUND = "Localidad %d no existe";
  private LocalidadRepository localidadRepository;

  public LocalidadServiceImpl(LocalidadRepository localidadRepository) {
    this.localidadRepository = localidadRepository;
  }

  @Override
  public Lista<Localidad> listar(String busqueda, int pagina, int limite) {
    return Lista.of(localidadRepository.findByNombreContainingIgnoreCase(busqueda, PageRequest.of(pagina, limite)));
  }

  @Override
  public Localidad consultar(long id) {
    return localidadRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format(MSG_NOT_FOUND, id)));
  }

  @Override
  public Localidad crear(Localidad localidad) {
    localidad.setId(null);
    return localidadRepository.save(localidad);
  }

  @Override
  public Localidad guardar(Localidad localidad) {
    if (!localidadRepository.existsById(localidad.getId())) {
      throw new NotFoundException(String.format(MSG_NOT_FOUND, localidad.getId()));
    }
    return localidadRepository.save(localidad);
  }

  @Override
  public void borrar(long id) {
    if (localidadRepository.existsById(id)) {
      localidadRepository.deleteById(id);
    }
  }

}