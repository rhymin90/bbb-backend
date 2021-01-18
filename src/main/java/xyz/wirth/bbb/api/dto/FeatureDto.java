package xyz.wirth.bbb.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class FeatureDto {

  @NotBlank private String feature;
  private boolean enabled;
}
