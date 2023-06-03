package org.gnori.testtaskdigitalbudget.controller.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PageRequestBuilder {

  public static final String DEFAULT_PAGE_NUMBER = "0";
  public static final String DEFAULT_PAGE_SIZE = "15";

  public static PageRequest preparePageRequestWithPageAndSize(int page, int size) {

    if (page < 0) page = Integer.parseInt(DEFAULT_PAGE_NUMBER);
    if (size < 0) size = Integer.parseInt(DEFAULT_PAGE_SIZE);

    return PageRequest.of(page, size);
  }
}
