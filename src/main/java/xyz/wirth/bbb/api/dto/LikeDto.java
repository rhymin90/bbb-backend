package xyz.wirth.bbb.api.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
public class LikeDto {

  @NotBlank private String userId;

  @Min(1)
  private Long cardId;
}
