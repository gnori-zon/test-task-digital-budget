package org.gnori.testtaskdigitalbudget.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieDto {

  protected Integer id;

  protected String title;

  @JsonProperty("poster_path")
  protected String posterPath;

}
