package org.gnori.testtaskdigitalbudget.scheduler;

import java.util.List;
import org.gnori.testtaskdigitalbudget.model.dto.MovieDto;
import org.gnori.testtaskdigitalbudget.model.dto.external.MoviesFromExternalApiDto;
import org.gnori.testtaskdigitalbudget.model.dto.external.ResponseFromExternalApiDto;
import org.gnori.testtaskdigitalbudget.service.access.storage.impl.MovieService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@DisplayName("Unit test for ScheduledWorkerWithExternalApi")
class ScheduledWorkerWithExternalApiTest {

  private final String url = "http://testUrl";
  private final String token = "testToken";

  private final RestTemplate restTemplateMock = Mockito.mock(RestTemplate.class);
  private final MovieService movieServiceMock = Mockito.mock(MovieService.class);

  private final ScheduledWorkerWithExternalApi workerWithExternalApi =
      new ScheduledWorkerWithExternalApi(restTemplateMock, movieServiceMock, url, token);

  @Test
  void requestAndSaveNewMovieSuccess() {

    var body = ResponseFromExternalApiDto.builder()
        .page(1)
        .results(List.of(
            MoviesFromExternalApiDto.builder()
                .id(1)
                .title("title-1")
                .posterPath("path-1")
                .build(),
            MoviesFromExternalApiDto.builder()
                .id(2)
                .title("title-2")
                .posterPath("path-2")
                .build()
        ))
        .totalPages(12)
        .totalResults(2)
        .build();

    var responseEntity = new ResponseEntity<>(body, HttpStatus.OK);

    Mockito.when(restTemplateMock.exchange(
        Mockito.anyString(),
        Mockito.any(HttpMethod.class),
        Mockito.any(HttpEntity.class),
        Mockito.eq(ResponseFromExternalApiDto.class)
    )).thenReturn(responseEntity);

    var expectedMoviesDto = responseEntity.getBody().getResults()
        .stream()
        .map(
            moviesFromExternalApiDto -> MovieDto.builder()
                .title(moviesFromExternalApiDto.getTitle())
                .posterPath(moviesFromExternalApiDto.getPosterPath())
                .build()
        );

    workerWithExternalApi.requestAndSaveNewMovies();

    expectedMoviesDto.forEach(
        movieDto -> Mockito
            .verify(movieServiceMock, Mockito.times(5))
            .createIfExist(movieDto)
    );
  }

  @Test
  void requestAndSaveNewMovieWhenThrewRestClientException() {

    Mockito.when(restTemplateMock.exchange(
        Mockito.anyString(),
        Mockito.any(HttpMethod.class),
        Mockito.any(HttpEntity.class),
        Mockito.eq(ResponseFromExternalApiDto.class)
    )).thenThrow(RestClientException.class);

    Assertions.assertDoesNotThrow(workerWithExternalApi::requestAndSaveNewMovies);
  }
}