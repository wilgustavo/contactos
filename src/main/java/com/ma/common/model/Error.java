
package com.ma.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Error
 */
@Data
@AllArgsConstructor
public class Error {

  private String campo;
  private String texto;
  
  
}