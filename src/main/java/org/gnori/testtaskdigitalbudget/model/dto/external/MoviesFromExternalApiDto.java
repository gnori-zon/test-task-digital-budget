package org.gnori.testtaskdigitalbudget.model.dto.external;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MoviesFromExternalApiDto {

  protected Boolean adult;

  @JsonProperty("backdrop_path")
  protected String backdropPath;

  @JsonProperty("genre_ids")
  protected List<Integer> genreIds;

  protected Integer id;

  @JsonProperty("original_language")
  protected String originalLanguage;

  @JsonProperty("original_title")
  protected String originalTitle;

  protected String overview;

  protected Double popularity;

  @JsonProperty("poster_path")
  protected String posterPath;

  @JsonProperty("release_date")
  protected String releaseDate;

  protected String title;

  protected Boolean video;

  @JsonProperty("vote_average")
  protected Double voteAverage;

  @JsonProperty("vote_count")
  protected Integer voteCount;

}
