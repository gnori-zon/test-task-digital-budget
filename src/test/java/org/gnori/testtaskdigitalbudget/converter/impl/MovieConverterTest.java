package org.gnori.testtaskdigitalbudget.converter.impl;

import java.util.List;
import org.gnori.testtaskdigitalbudget.model.dto.MovieDto;
import org.gnori.testtaskdigitalbudget.model.entity.impl.MovieEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;

@DisplayName("Unit test for MovieConverter")
class MovieConverterTest {

  private final MovieConverter converter = new MovieConverter();
  private MovieEntity rawMovieEntity;
  private MovieDto rawMovieDto;

  @BeforeEach
  void setUp() {
    rawMovieEntity = MovieEntity.builder()
        .title("titleEntity")
        .posterPath("pathEntity")
        .build();
    rawMovieEntity.setId(1);

    rawMovieDto = MovieDto.builder()
        .id(2)
        .title("titleDto")
        .posterPath("pathDto")
        .build();
  }

  @Test
  void convertFromMovieDto() {

    var actualEntity = converter.convertFrom(rawMovieDto);

    Assertions.assertEquals(rawMovieDto.getPosterPath(), actualEntity.getPosterPath());
    Assertions.assertEquals(rawMovieDto.getTitle(), actualEntity.getTitle());
    Assertions.assertNotEquals(rawMovieDto.getId(), actualEntity.getId());
  }

  @Test
  void convertFromMovieEntity() {

    var actualDto = converter.convertFrom(rawMovieEntity);

    Assertions.assertEquals(rawMovieEntity.getPosterPath(), actualDto.getPosterPath());
    Assertions.assertEquals(rawMovieEntity.getTitle(), actualDto.getTitle());
    Assertions.assertEquals(rawMovieEntity.getId(), actualDto.getId());
  }

  @Test
  void convertPage() {

    var secondMovieEntity = MovieEntity.builder().title("title2").posterPath("path2").build();
    secondMovieEntity.setId(2);

    var thirdMovieEntity = MovieEntity.builder().title("title3").posterPath("path3").build();
    thirdMovieEntity.setId(2);

    var rawPage = new PageImpl<>(List.of(rawMovieEntity, secondMovieEntity, thirdMovieEntity));

    var actualPage = converter.convertPage(rawPage);

    Assertions.assertEquals(rawPage.getTotalPages(), actualPage.getTotalPages());
    Assertions.assertEquals(rawPage.getTotalElements(), actualPage.getTotalElements());
    Assertions.assertEquals(rawPage.getNumber(), actualPage.getNumber());
    Assertions.assertEquals(rawPage.getSize(), actualPage.getSize());
    Assertions.assertEquals(rawPage.getSort(), actualPage.getSort());
    Assertions.assertNotNull(actualPage.getContent());

    var actualContent = actualPage.getContent();
    var expectedContent = rawPage.getContent();

    Assertions.assertEquals(expectedContent.size(), actualContent.size());

    for (int i = 0; i < expectedContent.size(); i++) {
      Assertions.assertEquals(expectedContent.get(i).getTitle(), actualContent.get(i).getTitle());
      Assertions.assertEquals(expectedContent.get(i).getPosterPath(), actualContent.get(i).getPosterPath());
      Assertions.assertEquals(expectedContent.get(i).getId(), actualContent.get(i).getId());
    }
  }

  @Test
  void convertFromEntityList() {

    var secondMovieEntity = MovieEntity.builder().title("title2").posterPath("path2").build();
    secondMovieEntity.setId(2);

    var thirdMovieEntity = MovieEntity.builder().title("title3").posterPath("path3").build();
    thirdMovieEntity.setId(2);

    var rawList = List.of(rawMovieEntity, secondMovieEntity, thirdMovieEntity);

    var actualList = converter.convertList(rawList);

    Assertions.assertEquals(rawList.size(), actualList.size());

    for (int i = 0; i < rawList.size(); i++) {
      Assertions.assertEquals(rawList.get(i).getTitle(), actualList.get(i).getTitle());
      Assertions.assertEquals(rawList.get(i).getPosterPath(), actualList.get(i).getPosterPath());
      Assertions.assertEquals(rawList.get(i).getId(), actualList.get(i).getId());
    }

  }
}