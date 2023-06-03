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
public class ResponseFromExternalApiDto {

  protected Integer page;

  protected List<MoviesFromExternalApiDto> results;

  @JsonProperty("total_pages")
  protected Integer totalPages;

  @JsonProperty("total_results")
  protected Integer totalResults;

}
