package xyz.wirth.bbb.api.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Collections;
import java.util.List;

@Data
public class CardDto {

  private Long id;
  @NotBlank private String userId;
  @NotBlank private String text;

  @Min(value = 0)
  private Long created;

  private boolean deletable;

  private List<String> likes = Collections.emptyList();
}
