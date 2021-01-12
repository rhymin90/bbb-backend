package xyz.wirth.bbb.domain.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "dimissed", schema = "public")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Dismissed {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String userId;
  private int candidateId;
  private int episode;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Dismissed)) {
      return false;
    }
    Dismissed other = (Dismissed) o;
    return id != null && id.equals(other.getId());
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
