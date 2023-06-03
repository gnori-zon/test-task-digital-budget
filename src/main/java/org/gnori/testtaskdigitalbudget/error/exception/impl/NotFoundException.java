package org.gnori.testtaskdigitalbudget.error.exception.impl;

import org.gnori.testtaskdigitalbudget.error.exception.BusinessLogicException;
import org.springframework.http.HttpStatus;

public class NotFoundException extends BusinessLogicException {

  public NotFoundException(String message) {
    super(message, HttpStatus.NOT_FOUND);
  }
}
