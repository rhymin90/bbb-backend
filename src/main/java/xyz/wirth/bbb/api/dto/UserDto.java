package xyz.wirth.bbb.api.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserDto {

  @NotBlank private String email;
  @NotBlank private String name;
  @NotBlank private String profile;
  @NotBlank private String provider;
  private boolean verified;
  private boolean tvNow = false;
}
