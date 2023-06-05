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
  private Integer currentPage;
  private Integer totalPage;

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
    currentPage = 1;
    totalPage = 1;

  }

  @Scheduled(fixedDelayString = "${worker.requestInterval}")
  public void requestAndSaveNewMovies() {

    var requestEntity = prepareHttpEntityWithAcceptHeaderAndBearerToken(
        List.of(MediaType.APPLICATION_JSON),
        token
    );

    if (currentPage > totalPage) currentPage--;

    var numberLastPage = currentPage + 5;

    for (int page = currentPage; page < numberLastPage && page <= totalPage; page++) {
      var uri = prepareCommonUriForGetMoviesWithPage(page, url);

      var optionalResponseEntity = getResponseFromExternalApi(uri, METHOD_GET, requestEntity);

      optionalResponseEntity.ifPresent(
          responseEntity -> {
            var body = responseEntity.getBody();
            if (body != null) {
              totalPage =  body.getTotalPages();
              saveAllNewMovie(body);
            }
          }
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
