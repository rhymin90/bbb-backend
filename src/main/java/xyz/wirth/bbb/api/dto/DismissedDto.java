package xyz.wirth.bbb.api.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
public class DismissedDto {

  private Long id;

  @NotBlank private String userId;

  @Min(1)
  private int candidateId;

  @Min(1)
  private int episode;
}
