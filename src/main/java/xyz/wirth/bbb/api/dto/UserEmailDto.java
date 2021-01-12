package xyz.wirth.bbb.api.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class UserEmailDto {

  @NotBlank private String email;
  private boolean exists;
}
