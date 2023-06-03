package org.gnori.testtaskdigitalbudget.controller;

import static org.gnori.testtaskdigitalbudget.controller.UserController.AUTH_HEADER;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.gnori.testtaskdigitalbudget.model.dto.MovieDto;
import org.gnori.testtaskdigitalbudget.service.access.storage.FavoriteMoviesService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FavoriteMoviesController {

  public static final String FAVORITES_URL = "/api/v1/users/favorites";
  public static final String NON_FAVORITES_URL = "/api/v1/users/non-favorites";
  public static final String POST_PATH = ":add";
  public static final String DELETE_PATH = ":delete";

  private final FavoriteMoviesService<MovieDto> favoriteMoviesService;

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping(FAVORITES_URL + POST_PATH)
  public MovieDto addInFavorites(
      @RequestHeader(AUTH_HEADER) Integer userId,
      @RequestParam Integer movieId
  ) {

    return favoriteMoviesService.addInFavoritesByUserIdAndMovieId(userId, movieId);
  }

  @GetMapping(NON_FAVORITES_URL)
  public List<MovieDto> getAllNonFavoriteMovies(
      @RequestHeader(name = AUTH_HEADER) Integer userId,
      @RequestParam String loaderType
  ) {

    return favoriteMoviesService.searchAllNonFavoriteByLoaderTypeAndUserId(loaderType, userId);
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping(FAVORITES_URL + DELETE_PATH)
  public void deleteFromFavorites(
      @RequestHeader(AUTH_HEADER) Integer userId,
      @RequestParam Integer movieId
  ) {

    favoriteMoviesService.deleteFromFavoritesByUserIdAndMovieId(userId, movieId);
  }

}
