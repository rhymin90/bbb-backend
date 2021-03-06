package xyz.wirth.bbb.domain.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "likes", schema = "public")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@IdClass(LikeId.class)
public class Like {

  @Id private Long cardId;
  @Id private String userId;

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
