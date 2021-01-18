package xyz.wirth.bbb.api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EpisodeDto {

  private int episode;
  private long date;
  private int amountDismissed;
}
