package xyz.wirth.bbb.domain.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "likes", schema = "public")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Like {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long cardId;
  private String userId;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Card)) {
      return false;
    }
    Card other = (Card) o;
    return id != null && id.equals(other.getId());
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
