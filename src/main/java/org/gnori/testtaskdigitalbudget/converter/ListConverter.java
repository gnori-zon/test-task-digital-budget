package org.gnori.testtaskdigitalbudget.converter;

import java.util.List;
import org.gnori.testtaskdigitalbudget.model.entity.BaseEntity;

public interface ListConverter<D, E extends BaseEntity> {

  List<D> convertFrom(List<E> entityList);
}
