package org.gnori.testtaskdigitalbudget.model.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.gnori.testtaskdigitalbudget.validation.NotEmptyIfPresent;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

  public interface CreationValidation {}
  public interface UpdateValidation {}

  protected Integer id;

  @Email(message = "field 'email' contains non-email", groups = CreationValidation.class)
  @NotBlank(message = "field 'email' is missing or empty", groups = CreationValidation.class)
  protected String email;

  @NotEmptyIfPresent(message = "field 'username' is empty", groups = UpdateValidation.class)
  @Pattern(regexp = "^[a-zA-Z]+$", message = "field 'username' contains non-Latin letters",
      groups = {CreationValidation.class, UpdateValidation.class})
  @NotBlank(message = "field 'username' is missing or empty", groups = CreationValidation.class)
  protected String username;

  @NotEmptyIfPresent(message = "field 'name' is empty", groups = UpdateValidation.class)
  protected String name;
}
