package org.gnori.testtaskdigitalbudget.scheduler;

import static org.gnori.testtaskdigitalbudget.scheduler.UtilsForRequest.prepareCommonUriForGetMoviesWithPage;
import static org.gnori.testtaskdigitalbudget.scheduler.UtilsForRequest.prepareHttpEntityWithAcceptHeaderAndBearerToken;

import java.util.List;
import java.util.Optional;
import lombok.extern.log4j.Log4j2;
import org.gnori.testtaskdigitalbudget.model.dto.MovieDto;
import org.gnori.testtaskdigitalbudget.model.dto.external.ResponseFromExternalApiDto;
import org.gnori.testtaskdigitalbudget.service.access.storage.impl.MovieService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Log4j2
@Service
public class ScheduledWorkerWithExternalApi {

  public static final String ERROR_TEXT = "[ScheduledWorkerWithExternalApi] threw {} with message {} \n stack trace: {}";
  private static final HttpMethod METHOD_GET = HttpMethod.GET;

  private final RestTemplate restTemplate;
  private final MovieService movieService;
  private final String url;
  private final String token;

  public ScheduledWorkerWithExternalApi(
      RestTemplate restTemplate,
      MovieService movieService,
      @Value("${worker.url}") String url,
      @Value("${worker.token}") String token
      ) {

    this.restTemplate = restTemplate;
    this.movieService = movieService;
    this.url = url;
    this.token = token;
  }

  @Scheduled(fixedDelayString = "${worker.requestInterval}")
  public void requestAndSaveNewMovies() {

    var requestEntity = prepareHttpEntityWithAcceptHeaderAndBearerToken(
        List.of(MediaType.APPLICATION_JSON),
        token
    );

    for (int page = 1; page <= 5; page ++) {
      var uri = prepareCommonUriForGetMoviesWithPage(page, url);

      var optionalResponseEntity = getResponseFromExternalApi(uri, METHOD_GET, requestEntity);

      optionalResponseEntity.ifPresent(
          responseEntity -> saveAllNewMovie(responseEntity.getBody())
      );
    }
  }

  private Optional<ResponseEntity<ResponseFromExternalApiDto>> getResponseFromExternalApi(
      String uri,
      HttpMethod method,
      HttpEntity<?> requestEntity
  ) {
    try {
      return Optional.of(
          restTemplate.exchange(
              uri,
              method,
              requestEntity,
              ResponseFromExternalApiDto.class));

    } catch (RestClientException e) {
      log.error(ERROR_TEXT, e.getClass().getSimpleName(), e.getMessage(), e.getStackTrace());
    }

    return Optional.empty();
  }

  private void saveAllNewMovie(ResponseFromExternalApiDto responseFromExternalApi) {

    if (responseFromExternalApi != null) {
      responseFromExternalApi.getResults()
          .stream()
          .map(
              moviesFromExternalApiDto -> MovieDto.builder()
                  .title(moviesFromExternalApiDto.getTitle())
                  .posterPath(moviesFromExternalApiDto.getPosterPath())
                  .build()
          ).forEach(movieService::createIfExist);
    }
  }
}
