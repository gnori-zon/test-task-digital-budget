package org.gnori.testtaskdigitalbudget.converter.impl;

import java.util.List;
import java.util.stream.Collectors;
import org.gnori.testtaskdigitalbudget.converter.BaseConverter;
import org.gnori.testtaskdigitalbudget.converter.ListConverter;
import org.gnori.testtaskdigitalbudget.converter.PageConverter;
import org.gnori.testtaskdigitalbudget.model.dto.MovieDto;
import org.gnori.testtaskdigitalbudget.model.entity.impl.MovieEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

@Component
public class MovieConverter implements BaseConverter<MovieDto, MovieEntity>,
    PageConverter<MovieDto, MovieEntity>, ListConverter<MovieDto, MovieEntity> {

  @Override
  public MovieDto convertFrom(MovieEntity movieEntity) {

    return MovieDto.builder()
        .id(movieEntity.getId())
        .title(movieEntity.getTitle())
        .posterPath(movieEntity.getPosterPath())
        .build();
  }

  @Override
  public MovieEntity convertFrom(MovieDto movieDto) {

    return MovieEntity.builder()
        .title(movieDto.getTitle())
        .posterPath(movieDto.getPosterPath())
        .build();
  }

  @Override
  public Page<MovieDto> convertPage(Page<MovieEntity> movieEntityPage) {

    return  new PageImpl<>(
        movieEntityPage.map(this::convertFrom).toList(),
        movieEntityPage.getPageable(),
        movieEntityPage.getTotalElements()
    );
  }

  @Override
  public List<MovieDto> convertList(List<MovieEntity> entityList) {

    return entityList.stream().map(this::convertFrom).collect(Collectors.toList());
  }
}
