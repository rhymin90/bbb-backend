package xyz.wirth.bbb.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FinalistsDto {

  @NotBlank private String userId;

  @PositiveOrZero private int winner;
  private long winnerDate;
  @PositiveOrZero private int second;
  private long secondDate;
}
