package org.gnori.testtaskdigitalbudget.service.search;

public enum LoaderType {
  SQL("sql"),
  IN_MEMORY("inMemory");

  private final String stringRepresentation;

  LoaderType(String stringRepresentation) {
    this.stringRepresentation = stringRepresentation;
  }

  public String getStringRepresentation() {
    return stringRepresentation;
  }
}
