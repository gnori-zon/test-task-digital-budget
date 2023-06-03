package org.gnori.testtaskdigitalbudget.service.access.storage.impl;

import org.gnori.testtaskdigitalbudget.converter.BaseConverter;
import org.gnori.testtaskdigitalbudget.converter.PageConverter;
import org.gnori.testtaskdigitalbudget.dao.iml.MovieDao;
import org.gnori.testtaskdigitalbudget.error.exception.impl.NotFoundException;
import org.gnori.testtaskdigitalbudget.model.dto.MovieDto;
import org.gnori.testtaskdigitalbudget.model.entity.impl.MovieEntity;
import org.gnori.testtaskdigitalbudget.service.access.storage.AbstractService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class MovieService extends AbstractService<MovieEntity, MovieDao> {

  private final BaseConverter<MovieDto, MovieEntity> movieConverter;
  private final PageConverter<MovieDto, MovieEntity> pageConverter;

  protected MovieService(
      MovieDao dao,
      BaseConverter<MovieDto, MovieEntity> movieConverter,
      PageConverter<MovieDto, MovieEntity> pageConverter
  ) {
    super(dao);
    this.movieConverter = movieConverter;
    this.pageConverter = pageConverter;
  }

  public void createIfExist(MovieDto movieDto) {

    var title = movieDto.getTitle();
    var posterPath = movieDto.getPosterPath();

    if (title != null && posterPath != null && !dao.existsByTitleAndPosterPath(title, posterPath)) {
      var movieEntity = movieConverter.convertFrom(movieDto);
      create(movieEntity);
    }
  }

  public Page<MovieDto> getMoviesDtoPage(PageRequest pageParams) {

    var movieEntityPage = dao.findAll(pageParams);

    return pageConverter.convertPage(movieEntityPage);
  }

  public MovieEntity getMovieEntityById(Integer movieId) {

    return checkForExistAndGetById(movieId);
  }

  private MovieEntity checkForExistAndGetById(Integer movieId) {

    return get(movieId).orElseThrow(
        () -> new NotFoundException(
            String.format("movie with id: %d is not exist", movieId))
    );
  }
}
