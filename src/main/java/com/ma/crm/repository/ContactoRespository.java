package com.ma.crm.repository;

import com.ma.crm.model.Contacto;
import com.ma.crm.model.ContactoItem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * ContactoRespository
 */
public interface ContactoRespository extends JpaRepository<Contacto, Long>{

  Page<ContactoItem> findByNombreContainingIgnoreCase(String nombre, Pageable pageable);

  
}