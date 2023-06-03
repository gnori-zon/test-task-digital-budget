package org.gnori.testtaskdigitalbudget.controller;

import static org.gnori.testtaskdigitalbudget.controller.MovieController.MOVIE_URL;
import static org.gnori.testtaskdigitalbudget.controller.utils.PageRequestBuilder.DEFAULT_PAGE_NUMBER;
import static org.gnori.testtaskdigitalbudget.controller.utils.PageRequestBuilder.DEFAULT_PAGE_SIZE;
import static org.gnori.testtaskdigitalbudget.controller.utils.PageRequestBuilder.preparePageRequestWithPageAndSize;

import lombok.RequiredArgsConstructor;
import org.gnori.testtaskdigitalbudget.model.dto.MovieDto;
import org.gnori.testtaskdigitalbudget.service.access.storage.impl.MovieService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(MOVIE_URL)
@RequiredArgsConstructor
public class MovieController {

  public static final String MOVIE_URL = "/api/v1/movies";

  private final MovieService movieService;

  @ResponseStatus(HttpStatus.OK)
  @GetMapping
  public Page<MovieDto> getMoviesPage(
      @RequestParam(defaultValue = DEFAULT_PAGE_NUMBER) Integer page,
      @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) Integer size
  ) {

    var pageParams = preparePageRequestWithPageAndSize(page, size);

    return movieService.getMoviesDtoPage(pageParams);
  }

}
