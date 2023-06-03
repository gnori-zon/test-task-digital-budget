package org.gnori.testtaskdigitalbudget.service.search.searcher;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.gnori.testtaskdigitalbudget.converter.ListConverter;
import org.gnori.testtaskdigitalbudget.dao.iml.MovieDao;
import org.gnori.testtaskdigitalbudget.model.dto.MovieDto;
import org.gnori.testtaskdigitalbudget.model.entity.impl.MovieEntity;
import org.gnori.testtaskdigitalbudget.service.search.NonFavoritesMoviesSearcher;

@RequiredArgsConstructor
public class SqlNonFavoritesMoviesSearcher implements NonFavoritesMoviesSearcher {

  private final MovieDao movieDao;
  private final ListConverter<MovieDto, MovieEntity> movieListConverter;

  @Override
  public List<MovieDto> search(Integer userId) {

    var movieEntityList = movieDao.findAllNonFavoritesByUserId(userId);

    return movieListConverter.convertFrom(movieEntityList);
  }
}
