package org.gnori.testtaskdigitalbudget.service.search;

import java.util.HashMap;
import java.util.Map;
import org.gnori.testtaskdigitalbudget.converter.ListConverter;
import org.gnori.testtaskdigitalbudget.dao.iml.MovieDao;
import org.gnori.testtaskdigitalbudget.dao.iml.UserDao;
import org.gnori.testtaskdigitalbudget.model.dto.MovieDto;
import org.gnori.testtaskdigitalbudget.model.entity.impl.MovieEntity;
import org.gnori.testtaskdigitalbudget.service.search.searcher.InMemoryNonFavoritesMoviesSearcher;
import org.gnori.testtaskdigitalbudget.service.search.searcher.SqlNonFavoritesMoviesSearcher;
import org.gnori.testtaskdigitalbudget.service.search.searcher.UnknownMoviesSearcher;
import org.springframework.stereotype.Component;

@Component
public class NonFavoritesMoviesSearcherContainer {

  private final Map<String, NonFavoritesMoviesSearcher> searcherMap;
  private final NonFavoritesMoviesSearcher unknownMoviesSearcher;

  public NonFavoritesMoviesSearcherContainer(
      MovieDao movieDao,
      UserDao userDao,
      ListConverter<MovieDto, MovieEntity> movieListConverter
  ) {
     searcherMap = new HashMap<>();

     searcherMap.put(
         LoaderType.SQL.getStringRepresentation(),
         new SqlNonFavoritesMoviesSearcher(movieDao, userDao, movieListConverter));

     searcherMap.put(
         LoaderType.IN_MEMORY.getStringRepresentation(),
         new InMemoryNonFavoritesMoviesSearcher(movieDao, userDao, movieListConverter));

     unknownMoviesSearcher = new UnknownMoviesSearcher();
  }

  public NonFavoritesMoviesSearcher retrieveSearcher(String loaderType){

    return searcherMap.getOrDefault(loaderType,unknownMoviesSearcher);
  }

}
