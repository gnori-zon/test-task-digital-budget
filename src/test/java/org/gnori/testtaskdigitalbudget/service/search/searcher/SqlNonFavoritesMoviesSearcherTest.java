package org.gnori.testtaskdigitalbudget.service.search.searcher;

import java.util.List;
import org.gnori.testtaskdigitalbudget.converter.ListConverter;
import org.gnori.testtaskdigitalbudget.converter.impl.MovieConverter;
import org.gnori.testtaskdigitalbudget.dao.iml.MovieDao;
import org.gnori.testtaskdigitalbudget.dao.iml.UserDao;
import org.gnori.testtaskdigitalbudget.error.exception.impl.NotFoundException;
import org.gnori.testtaskdigitalbudget.model.dto.MovieDto;
import org.gnori.testtaskdigitalbudget.model.entity.impl.MovieEntity;
import org.gnori.testtaskdigitalbudget.model.entity.impl.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@DisplayName("Integration test for SqlNonFavoritesMoviesSearcher and JPA")
class SqlNonFavoritesMoviesSearcherTest {

  private final MovieDao movieDao;
  private final UserDao userDao;
  private final ListConverter<MovieDto, MovieEntity> converter;
  private final SqlNonFavoritesMoviesSearcher searcher;

  SqlNonFavoritesMoviesSearcherTest(@Autowired MovieDao movieDao, @Autowired UserDao userDao) {
    this.movieDao = movieDao;
    this.userDao = userDao;
    converter = new MovieConverter();
    searcher = new SqlNonFavoritesMoviesSearcher(movieDao, userDao, converter);
  }

  @Test
  void searchSuccess() {

    var favoritesMovie =  MovieEntity.builder()
        .title("title-1")
        .posterPath("path-1")
        .build();

    var nonFavoritesMovie = MovieEntity.builder()
        .title("title-2")
        .posterPath("path-2")
        .build();

    favoritesMovie = movieDao.saveAndFlush(favoritesMovie);
    nonFavoritesMovie = movieDao.saveAndFlush(nonFavoritesMovie);

    var user = UserEntity.builder()
        .username("user")
        .email("mail@mail.ru")
        .favorites(List.of(favoritesMovie))
        .build();

    user = userDao.saveAndFlush(user);

    var expectedList = converter.convertList(List.of(nonFavoritesMovie));

    var actualList = searcher.search(user.getId());

    Assertions.assertEquals(expectedList, actualList);

    userDao.deleteById(user.getId());
    movieDao.deleteAllById(List.of(nonFavoritesMovie.getId(), favoritesMovie.getId()));
  }

  @Test
  void searchShouldThrowNotFoundException() {

    Assertions.assertThrows(NotFoundException.class, () -> searcher.search(1_00));

  }
}