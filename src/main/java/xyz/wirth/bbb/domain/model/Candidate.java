package xyz.wirth.bbb.domain.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "candidates", schema = "public")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Candidate {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String firstName;
  private String lastName;
  private int age;
  private String location;
  private String job;
  private String info;
  private int episode = -1;
  private String image;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Candidate)) {
      return false;
    }
    Candidate other = (Candidate) o;
    return id != null && id.equals(other.getId());
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
