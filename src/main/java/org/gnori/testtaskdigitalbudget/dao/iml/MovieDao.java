package org.gnori.testtaskdigitalbudget.dao.iml;

import java.util.List;
import org.gnori.testtaskdigitalbudget.dao.BaseDao;
import org.gnori.testtaskdigitalbudget.model.entity.impl.MovieEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MovieDao extends BaseDao<MovieEntity> {

  boolean existsByTitleAndPosterPath(String title, String posterPath);

  @Query(nativeQuery = true,
      value = "select m.* "
          + "from movie as m "
          + "where m.id not in (select f.movie_id from favorites as f where f.user_id = :userId)")
  List<MovieEntity> findAllNonFavoritesByUserId(@Param("userId")Integer userId);
}
