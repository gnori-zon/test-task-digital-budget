package org.gnori.testtaskdigitalbudget.service.search.searcher;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.gnori.testtaskdigitalbudget.converter.ListConverter;
import org.gnori.testtaskdigitalbudget.dao.iml.MovieDao;
import org.gnori.testtaskdigitalbudget.dao.iml.UserDao;
import org.gnori.testtaskdigitalbudget.error.exception.impl.NotFoundException;
import org.gnori.testtaskdigitalbudget.model.dto.MovieDto;
import org.gnori.testtaskdigitalbudget.model.entity.impl.MovieEntity;
import org.gnori.testtaskdigitalbudget.service.search.NonFavoritesMoviesSearcher;

@RequiredArgsConstructor
public class SqlNonFavoritesMoviesSearcher implements NonFavoritesMoviesSearcher {

  private final MovieDao movieDao;
  private final UserDao userDao;
  private final ListConverter<MovieDto, MovieEntity> movieListConverter;

  @Override
  public List<MovieDto> search(Integer userId) {

    if (!userDao.existsById(userId)) {
      throw  new NotFoundException(String.format("user with id: %d not exist", userId));
    }

    var movieEntityList = movieDao.findAllNonFavoritesByUserId(userId);

    return movieListConverter.convertList(movieEntityList);
  }
}
