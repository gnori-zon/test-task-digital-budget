package org.gnori.testtaskdigitalbudget.model.dto.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class ErrorDto {

  protected String error;

  @JsonProperty("error_description")
  protected String errorDescription;

  @JsonProperty("fields_errors")
  protected Map<String, String> fieldsErrors;
}
