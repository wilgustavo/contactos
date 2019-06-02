package com.ma.crm.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Value;

/**
 * ContactoItem
 */
@Value @JsonInclude(Include.NON_NULL)
public class ContactoItem {

  private Long id;
  private String nombre;
  private String email;
  private String telefono; 

}