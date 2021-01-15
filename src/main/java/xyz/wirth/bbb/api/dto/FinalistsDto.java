package xyz.wirth.bbb.api.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

@Data
public class FinalistsDto {

  @NotBlank private String userId;

  @PositiveOrZero private Long winner;

  @PositiveOrZero private Long second;
}
