package xyz.wirth.bbb.api.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

@Data
@Builder
public class FinalistsDto {

  @NotBlank private String userId;

  @PositiveOrZero private int winner;

  @PositiveOrZero private int second;
}
