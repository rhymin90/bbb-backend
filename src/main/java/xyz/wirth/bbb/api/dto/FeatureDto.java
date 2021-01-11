package xyz.wirth.bbb.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FeatureDto {

  private String feature;
  private boolean enabled;
}
