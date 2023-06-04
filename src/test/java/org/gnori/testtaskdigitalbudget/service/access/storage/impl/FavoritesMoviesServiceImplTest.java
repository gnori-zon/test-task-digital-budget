package org.gnori.testtaskdigitalbudget.service.access.storage.impl;

import java.util.ArrayList;
import java.util.List;
import org.gnori.testtaskdigitalbudget.converter.BaseConverter;
import org.gnori.testtaskdigitalbudget.converter.impl.MovieConverter;
import org.gnori.testtaskdigitalbudget.model.dto.MovieDto;
import org.gnori.testtaskdigitalbudget.model.entity.impl.MovieEntity;
import org.gnori.testtaskdigitalbudget.model.entity.impl.UserEntity;
import org.gnori.testtaskdigitalbudget.service.search.NonFavoritesMoviesSearcherContainer;
import org.gnori.testtaskdigitalbudget.service.search.searcher.SqlNonFavoritesMoviesSearcher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@DisplayName("Unit test for FavoritesMoviesServiceImplTest")
class FavoritesMoviesServiceImplTest {

  private final UserService userService;
  private final MovieService movieService;
  private final BaseConverter<MovieDto, MovieEntity> movieConverter;
  private final NonFavoritesMoviesSearcherContainer nonFavoritesMoviesSearcherContainer;
  private final FavoritesMoviesServiceImpl favoritesMoviesServiceImpl;

  private Integer movieId;
  private Integer userId;
  private MovieEntity favoritesMovie;
  private UserEntity user;

  FavoritesMoviesServiceImplTest() {
    this.userService = Mockito.mock(UserService.class);
    this.movieService = Mockito.mock(MovieService.class);
    this.movieConverter = new MovieConverter();
    this.nonFavoritesMoviesSearcherContainer =
        Mockito.mock(NonFavoritesMoviesSearcherContainer.class);

    this.favoritesMoviesServiceImpl = new FavoritesMoviesServiceImpl(
        userService,
        movieService,
        movieConverter,
        nonFavoritesMoviesSearcherContainer
    );
  }

  @BeforeEach
  void setUp(){

    movieId = 1;
    favoritesMovie =  MovieEntity.builder()
        .title("title-1")
        .posterPath("path-1")
        .build();
    favoritesMovie.setId(movieId);

    userId = 1;
    user = UserEntity.builder()
        .username("user")
        .email("mail@mail.ru")
        .favorites(new ArrayList<>())
        .build();
    user.setId(userId);
  }

  @Test
  void addInFavoritesByUserIdAndMovieId() {

    Mockito.when(userService.getUserEntityById(userId)).thenReturn(user);
    Mockito.when(movieService.getMovieEntityById(movieId)).thenReturn(favoritesMovie);

    var expectedMovieDto = movieConverter.convertFrom(favoritesMovie);

    Assertions.assertEquals(0, user.getFavorites().size());

    var actualMovieDto =
        favoritesMoviesServiceImpl.addInFavoritesByUserIdAndMovieId(userId, movieId);

    Assertions.assertEquals(expectedMovieDto, actualMovieDto);
    Assertions.assertEquals(1, user.getFavorites().size());

    Mockito.verify(userService).update(userId, user);

  }

  @Test
  void deleteFromFavoritesByUserIdAndMovieId() {

    user.setFavorites(new ArrayList<>(List.of(favoritesMovie)));

    Mockito.when(userService.getUserEntityById(userId)).thenReturn(user);
    Mockito.when(movieService.getMovieEntityById(movieId)).thenReturn(favoritesMovie);

    Assertions.assertEquals(1, user.getFavorites().size());

    favoritesMoviesServiceImpl.deleteFromFavoritesByUserIdAndMovieId(userId, movieId);

    Assertions.assertEquals(0, user.getFavorites().size());

    Mockito.verify(userService).update(userId, user);
  }

  @Test
  void searchAllNonFavoriteByLoaderTypeAndUserId() {

    var loaderType = "sql";
    var searcherMock = Mockito.mock(SqlNonFavoritesMoviesSearcher.class);

    Mockito.when(nonFavoritesMoviesSearcherContainer.retrieveSearcher(loaderType))
        .thenReturn(searcherMock);

    favoritesMoviesServiceImpl.searchAllNonFavoriteByLoaderTypeAndUserId(loaderType, userId);

    Mockito.verify(nonFavoritesMoviesSearcherContainer).retrieveSearcher(loaderType);
    Mockito.verify(searcherMock).search(userId);
  }
}