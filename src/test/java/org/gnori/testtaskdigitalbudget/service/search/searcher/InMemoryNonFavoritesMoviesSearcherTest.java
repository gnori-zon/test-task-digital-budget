package org.gnori.testtaskdigitalbudget.service.search.searcher;

import java.util.List;
import java.util.Optional;
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
import org.mockito.Mockito;

@DisplayName("Unit test for InMemoryNonFavoritesMoviesSearcher")
class InMemoryNonFavoritesMoviesSearcherTest {

  private final MovieDao movieDao;
  private final UserDao userDao;
  private final ListConverter<MovieDto, MovieEntity> converter;
  private final InMemoryNonFavoritesMoviesSearcher searcher;

  InMemoryNonFavoritesMoviesSearcherTest() {
    converter = new MovieConverter();
    movieDao = Mockito.mock(MovieDao.class);
    userDao = Mockito.mock(UserDao.class);
    searcher = new InMemoryNonFavoritesMoviesSearcher(movieDao, userDao, converter);
  }

  @Test
  void searchSuccess() {

    var userId = 1;

    var favoritesMovie =  MovieEntity.builder()
        .title("title-1")
        .posterPath("path-1")
        .build();
    favoritesMovie.setId(1);

    var nonFavoritesMovie = MovieEntity.builder()
        .title("title-2")
        .posterPath("path-2")
        .build();
    nonFavoritesMovie.setId(2);

    var user = UserEntity.builder()
        .username("user")
        .email("mail@mail.ru")
        .favorites(List.of(favoritesMovie))
        .build();
    user.setId(1);

    Mockito.when(userDao.findById(userId)).thenReturn(Optional.of(user));
    Mockito.when(movieDao.findAll()).thenReturn(List.of(favoritesMovie, nonFavoritesMovie));

    var expectedList = converter.convertList(List.of(nonFavoritesMovie));

    var actualList = searcher.search(user.getId());

    Assertions.assertEquals(expectedList, actualList);
  }

  @Test
  void searchShouldThrowNotFoundException() {

    var userId = 1;
    Mockito.when(userDao.findById(userId)).thenReturn(Optional.empty());

    Assertions.assertThrows(
        NotFoundException.class, () -> searcher.search(userId)
    );
  }
}