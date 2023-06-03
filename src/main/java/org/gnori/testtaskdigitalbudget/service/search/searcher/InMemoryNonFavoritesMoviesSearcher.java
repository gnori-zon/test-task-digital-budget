package org.gnori.testtaskdigitalbudget.service.search.searcher;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.gnori.testtaskdigitalbudget.converter.ListConverter;
import org.gnori.testtaskdigitalbudget.dao.iml.MovieDao;
import org.gnori.testtaskdigitalbudget.dao.iml.UserDao;
import org.gnori.testtaskdigitalbudget.error.exception.impl.NotFoundException;
import org.gnori.testtaskdigitalbudget.model.dto.MovieDto;
import org.gnori.testtaskdigitalbudget.model.entity.BaseEntity;
import org.gnori.testtaskdigitalbudget.model.entity.impl.MovieEntity;
import org.gnori.testtaskdigitalbudget.service.search.NonFavoritesMoviesSearcher;

@RequiredArgsConstructor
public class InMemoryNonFavoritesMoviesSearcher implements NonFavoritesMoviesSearcher {

  private final MovieDao movieDao;
  private final UserDao userDao;
  private final ListConverter<MovieDto, MovieEntity> movieListConverter;

  @Override
  public List<MovieDto> search(Integer userId) {

    var setUserMovieId = userDao.findById(userId).orElseThrow(
        () -> new NotFoundException(String.format("user with id: %d not exist", userId))
    ).getFavorites()
        .stream()
        .map(BaseEntity::getId)
        .collect(Collectors.toSet());

    var allMovie = movieDao.findAll();

    var allNonFavorites = allMovie.stream()
        .filter(movieEntity -> !setUserMovieId.contains(movieEntity.getId()))
        .collect(Collectors.toList());

    return movieListConverter.convertFrom(allNonFavorites);
  }

}
