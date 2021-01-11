package xyz.wirth.bbb.api.dto;

import lombok.Data;

@Data
public class DismissedDto {

  private String userId;
  private long candidateId;
  private int episode;
}
