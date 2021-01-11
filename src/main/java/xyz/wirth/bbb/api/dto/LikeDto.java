package xyz.wirth.bbb.api.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LikeDto {

  private Long id;
  @NotBlank private String userId;
  @NotBlank private Long cardId;
}
