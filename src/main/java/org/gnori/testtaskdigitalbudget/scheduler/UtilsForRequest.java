package org.gnori.testtaskdigitalbudget.scheduler;

import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.util.UriComponentsBuilder;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UtilsForRequest {

  public static String prepareCommonUriForGetMoviesWithPage(int page, String url) {

    return UriComponentsBuilder.fromHttpUrl(url)
        .queryParam("include_adult", false)
        .queryParam("include_video", false)
        .queryParam("language", "en-US")
        .queryParam("page", page)
        .queryParam("sort_by", "primary_release_date.asc")
        .toUriString();
  }

  public static HttpEntity<HttpHeaders> prepareHttpEntityWithAcceptHeaderAndBearerToken(
      List<MediaType> acceptHeaders,
      String token
  ) {

    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(acceptHeaders);
    headers.setBearerAuth(token);
    return new HttpEntity<>(headers);
  }
}
