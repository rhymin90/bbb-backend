package xyz.wirth.bbb.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LikeId implements Serializable {
  private Long cardId;
  private String userId;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Like)) {
      return false;
    }
    Like other = (Like) o;
    return cardId.equals(other.getCardId()) && userId.equals(other.getUserId());
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
