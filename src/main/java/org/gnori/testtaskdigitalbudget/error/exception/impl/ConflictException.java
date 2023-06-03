package org.gnori.testtaskdigitalbudget.error.exception.impl;

import org.gnori.testtaskdigitalbudget.error.exception.BusinessLogicException;
import org.springframework.http.HttpStatus;

public class ConflictException extends BusinessLogicException {

  public ConflictException(String message) {
    super(message, HttpStatus.CONFLICT);
  }
}
