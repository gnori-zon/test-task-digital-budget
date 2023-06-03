package org.gnori.testtaskdigitalbudget.converter;

import org.gnori.testtaskdigitalbudget.model.entity.BaseEntity;
import org.springframework.data.domain.Page;

public interface PageConverter <D, E extends BaseEntity> {

  Page<D> convertPage(Page<E> movieEntityPage);
}
