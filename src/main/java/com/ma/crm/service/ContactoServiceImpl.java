package com.ma.crm.service;

import com.ma.common.exception.NotFoundException;
import com.ma.common.exception.ValidationException;
import com.ma.common.model.Lista;
import com.ma.crm.model.Contacto;
import com.ma.crm.model.ContactoItem;
import com.ma.crm.repository.ContactoRespository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * ContactoServiceImpl
 */
@Service
public class ContactoServiceImpl implements ContactoService {

  private static final String MSG_NOT_FOUND = "Localidad %d no existe";
  private ContactoRespository contactoRepository;
  private LocalidadService localidadService;

  public ContactoServiceImpl(ContactoRespository contactoRepository, LocalidadService localidadService) {
    this.contactoRepository = contactoRepository;
    this.localidadService = localidadService;
  }
  
  @Override
  public Lista<ContactoItem> listar(String busqueda, int pagina, int limite) {
    return Lista.of(contactoRepository.findByNombreContainingIgnoreCase(busqueda,
      PageRequest.of(pagina, limite, Sort.by("nombre"))));
  }

  @Override
  public Contacto consultar(long id) {
    return contactoRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format(MSG_NOT_FOUND, id)));
  }

  @Override
  public Contacto crear(Contacto contacto) {
    contacto.setId(null);
    setLocalidad(contacto);
    return contactoRepository.save(contacto);
  }

  private void setLocalidad(Contacto contacto){
    if (contacto.getLocalidad() != null && contacto.getLocalidad().getId() != null) {
      try {
        contacto.setLocalidad(localidadService.consultar(contacto.getLocalidad().getId()));  
      } catch (NotFoundException ex) {
        throw new ValidationException("localidad", "No existe localidad " + contacto.getLocalidad().getId());
      }
    }
  }
  
}