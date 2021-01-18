package xyz.wirth.bbb.api.dto;

import lombok.Data;

@Data
public class EpisodeDto {

  private int episode;
  private long date;
  private int amountDismissed;
}
