package xyz.wirth.bbb.domain.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "finalists", schema = "public")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Finalists {

  @Id private String userId;
  private Long winner;
  private Long second;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Finalists)) {
      return false;
    }
    Finalists other = (Finalists) o;
    return userId != null && userId.equals(other.getUserId());
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
