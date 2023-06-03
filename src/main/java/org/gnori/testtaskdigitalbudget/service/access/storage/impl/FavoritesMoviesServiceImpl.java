package org.gnori.testtaskdigitalbudget.service.access.storage.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.gnori.testtaskdigitalbudget.converter.BaseConverter;
import org.gnori.testtaskdigitalbudget.model.dto.MovieDto;
import org.gnori.testtaskdigitalbudget.model.entity.impl.MovieEntity;
import org.gnori.testtaskdigitalbudget.service.access.storage.FavoriteMoviesService;
import org.gnori.testtaskdigitalbudget.service.search.NonFavoritesMoviesSearcherContainer;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FavoritesMoviesServiceImpl implements FavoriteMoviesService<MovieDto> {

  private final UserService userService;
  private final MovieService movieService;
  private final BaseConverter<MovieDto, MovieEntity> movieConverter;
  private final NonFavoritesMoviesSearcherContainer nonFavoritesMoviesSearcherContainer;

  @Override
  public MovieDto addInFavoritesByUserIdAndMovieId(Integer userId, Integer movieId) {

    var user = userService.getUserEntityById(userId);

    var movie = movieService.getMovieEntityById(movieId);

    user.getFavorites().add(movie);

    userService.update(userId, user);

    return movieConverter.convertFrom(movie);
  }

  @Override
  public void deleteFromFavoritesByUserIdAndMovieId(Integer userId, Integer movieId) {

    var user = userService.getUserEntityById(userId);

    var movie = movieService.getMovieEntityById(movieId);

    user.getFavorites().remove(movie);

    userService.update(userId, user);
  }

  @Override
  public List<MovieDto> searchAllNonFavoriteByLoaderTypeAndUserId(
      String loaderType, Integer userId
  ) {

    return nonFavoritesMoviesSearcherContainer.retrieveSearcher(loaderType).search(userId);
  }
}
