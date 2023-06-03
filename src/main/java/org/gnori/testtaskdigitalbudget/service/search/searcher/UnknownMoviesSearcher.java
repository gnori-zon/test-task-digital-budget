package org.gnori.testtaskdigitalbudget.service.search.searcher;

import java.util.List;
import org.gnori.testtaskdigitalbudget.error.exception.impl.NotFoundException;
import org.gnori.testtaskdigitalbudget.model.dto.MovieDto;
import org.gnori.testtaskdigitalbudget.service.search.NonFavoritesMoviesSearcher;

public class UnknownMoviesSearcher implements NonFavoritesMoviesSearcher {

  @Override
  public List<MovieDto> search(Integer userId) {
    throw new NotFoundException("unknown search type");
  }
}
