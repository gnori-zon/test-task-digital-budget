package org.gnori.testtaskdigitalbudget.converter;

import org.gnori.testtaskdigitalbudget.model.entity.BaseEntity;

public interface BaseConverter <D, E extends BaseEntity> {

  D convertFrom(E entity);

  E convertFrom(D dto);

}
