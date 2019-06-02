
package com.ma.crm.repository;

import com.ma.crm.model.Localidad;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * LocalidadRepository
 */
public interface LocalidadRepository extends JpaRepository<Localidad, Long> {

  /**
   * Listar localidades por nombre 
   * @param nombre el nombre o texto de busqueda.
   * @param pageable inicar los atributos de página
   * @return Lista de localidades
   */

  Page<Localidad> findByNombreContainingIgnoreCase(String nombre, Pageable pageable);
  
  
}