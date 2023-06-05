package org.gnori.testtaskdigitalbudget.service.access.storage.impl;

import java.util.List;
import java.util.Optional;
import org.gnori.testtaskdigitalbudget.converter.BaseConverter;
import org.gnori.testtaskdigitalbudget.converter.PageConverter;
import org.gnori.testtaskdigitalbudget.converter.impl.MovieConverter;
import org.gnori.testtaskdigitalbudget.dao.iml.MovieDao;
import org.gnori.testtaskdigitalbudget.error.exception.impl.NotFoundException;
import org.gnori.testtaskdigitalbudget.model.dto.MovieDto;
import org.gnori.testtaskdigitalbudget.model.entity.impl.MovieEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@DisplayName("Unit test for MovieService")
class MovieServiceTest {

  private final MovieDao movieDaoMock;
  private final PageConverter<MovieDto, MovieEntity> pageConverter;
  private final BaseConverter<MovieDto, MovieEntity> movieConverter;
  private final MovieService movieService;

  private MovieDto movieDto;

  MovieServiceTest() {
    var converter = new MovieConverter();
    movieDaoMock = Mockito.mock(MovieDao.class);
    pageConverter = converter;
    movieConverter = converter;
    this.movieService = new MovieService(movieDaoMock, movieConverter, pageConverter);
  }

  @BeforeEach
  void setUp() {
    movieDto = MovieDto.builder()
        .title("title-1")
        .posterPath("path-1")
        .build();
  }

  @Test
  void createIfExistSuccess() {

    Mockito.when(movieDaoMock.existsByTitleAndPosterPath(movieDto.getTitle(), movieDto.getPosterPath()))
        .thenReturn(false);
    Mockito.when(movieDaoMock.saveAndFlush(Mockito.any()))
        .thenReturn(movieConverter.convertFrom(movieDto));

    movieService.createIfExist(movieDto);

    Mockito.verify(movieDaoMock).saveAndFlush(Mockito.any());
  }

  @Test
  void createIfExistWhenBadConditions() {

    Mockito.when(movieDaoMock.existsByTitleAndPosterPath(movieDto.getTitle(), movieDto.getPosterPath()))
        .thenReturn(true);

    movieService.createIfExist(movieDto);
    Mockito.verify(movieDaoMock, Mockito.never()).saveAndFlush(Mockito.any());

    movieDto.setPosterPath(null);

    movieService.createIfExist(movieDto);
    Mockito.verify(movieDaoMock, Mockito.never()).saveAndFlush(Mockito.any());

    movieDto.setTitle(null);
    movieDto.setPosterPath("path-1");

    movieService.createIfExist(movieDto);
    Mockito.verify(movieDaoMock, Mockito.never()).saveAndFlush(Mockito.any());

  }

  @Test
  void getMoviesDtoPage() {

    var movieEntityPage = new PageImpl<>(List.of(
        MovieEntity.builder()
        .title(movieDto.getTitle())
        .posterPath(movieDto.getPosterPath())
        .build()
    ));

    var pageParams = PageRequest.of(1,2);
    Mockito.when(movieDaoMock.findAll(pageParams)).thenReturn(movieEntityPage);

    var expectedPage = pageConverter.convertPage(movieEntityPage);

    var actualPage = movieService.getMoviesDtoPage(pageParams);

    Assertions.assertEquals(expectedPage, actualPage);
  }

  @Test
  void getMovieEntityByIdSuccess() {

    var movieId = 1;
    var movieEntity = movieConverter.convertFrom(movieDto);
    movieEntity.setId(movieId);

    Mockito.when(movieDaoMock.findById(movieId)).thenReturn(Optional.of(movieEntity));

    var actualEntity = movieService.getMovieEntityById(movieId);

    Assertions.assertEquals(movieEntity, actualEntity);
  }

  @Test
  void getMovieEntityByIdShouldThrowNotFoundException() {

    var movieId = 1;

    Mockito.when(movieDaoMock.findById(movieId)).thenReturn(Optional.empty());

    Assertions.assertThrows(
        NotFoundException.class, () -> movieService.getMovieEntityById(movieId)
    );
  }

}