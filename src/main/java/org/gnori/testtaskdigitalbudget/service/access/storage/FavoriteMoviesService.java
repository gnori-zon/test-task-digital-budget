package org.gnori.testtaskdigitalbudget.service.access.storage;

import java.util.List;

public interface FavoriteMoviesService<D> {

  D addInFavoritesByUserIdAndMovieId(Integer userId, Integer movieId);

  void deleteFromFavoritesByUserIdAndMovieId(Integer userId, Integer movieId);

  List<D> searchAllNonFavoriteByLoaderTypeAndUserId(String loaderType, Integer userId);
}
