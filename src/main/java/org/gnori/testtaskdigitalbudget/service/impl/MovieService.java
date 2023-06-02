package org.gnori.testtaskdigitalbudget.service.impl;

import org.gnori.testtaskdigitalbudget.dao.iml.MovieDao;
import org.gnori.testtaskdigitalbudget.model.entity.impl.MovieEntity;
import org.gnori.testtaskdigitalbudget.service.AbstractService;
import org.springframework.stereotype.Service;

@Service
public class MovieService extends AbstractService<MovieEntity, MovieDao> {

  protected MovieService(MovieDao dao) {
    super(dao);
  }
}
