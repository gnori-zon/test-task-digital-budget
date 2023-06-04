package org.gnori.testtaskdigitalbudget.service.search;

import org.gnori.testtaskdigitalbudget.converter.ListConverter;
import org.gnori.testtaskdigitalbudget.converter.impl.MovieConverter;
import org.gnori.testtaskdigitalbudget.dao.iml.MovieDao;
import org.gnori.testtaskdigitalbudget.dao.iml.UserDao;
import org.gnori.testtaskdigitalbudget.model.dto.MovieDto;
import org.gnori.testtaskdigitalbudget.model.entity.impl.MovieEntity;
import org.gnori.testtaskdigitalbudget.service.search.searcher.InMemoryNonFavoritesMoviesSearcher;
import org.gnori.testtaskdigitalbudget.service.search.searcher.SqlNonFavoritesMoviesSearcher;
import org.gnori.testtaskdigitalbudget.service.search.searcher.UnknownMoviesSearcher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@DisplayName("Unit test for NonFavoritesMoviesSearcherContainer")
class NonFavoritesMoviesSearcherContainerTest {

  private final UserDao userDaoMock = Mockito.mock(UserDao.class);
  private final MovieDao movieDaoMock = Mockito.mock(MovieDao.class);
  private final ListConverter<MovieDto, MovieEntity> converter = new MovieConverter();

  private final NonFavoritesMoviesSearcherContainer container =
      new NonFavoritesMoviesSearcherContainer(movieDaoMock, userDaoMock, converter);

  @Test
  void retrieveSearcherWithKnownLoaderTypes() {

    NonFavoritesMoviesSearcher searcher = container.retrieveSearcher(LoaderType.SQL.getStringRepresentation());

    Assertions.assertTrue(searcher instanceof SqlNonFavoritesMoviesSearcher);

    searcher = container.retrieveSearcher(LoaderType.IN_MEMORY.getStringRepresentation());

    Assertions.assertTrue(searcher instanceof InMemoryNonFavoritesMoviesSearcher);
  }

  @Test
  void retrieveSearcherWithUnknownLoaderTypes() {

    NonFavoritesMoviesSearcher unknownSearcher = container.retrieveSearcher("bla-bla-bla");

    Assertions.assertTrue(unknownSearcher instanceof UnknownMoviesSearcher);

    unknownSearcher = container.retrieveSearcher("inHead");

    Assertions.assertTrue(unknownSearcher instanceof UnknownMoviesSearcher);
  }

}