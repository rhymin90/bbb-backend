package xyz.wirth.bbb.api.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
public class UserDto {

  // @NotBlank private String id;
  @NotBlank private String email;
  @NotBlank private String name;
  @NotBlank private String profile;
  private long finalist;
  private long winner;

  @Min(value = 0)
  private int score = 0;

  private boolean tvNow = false;
}
