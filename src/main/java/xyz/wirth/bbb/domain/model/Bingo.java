package xyz.wirth.bbb.domain.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "bingos", schema = "public")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Bingo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String userId;
  private Long episode;
  private String cards;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Bingo)) {
      return false;
    }
    Bingo other = (Bingo) o;
    return id.equals(other.getId());
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
