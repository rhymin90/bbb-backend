package xyz.wirth.bbb.domain.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users", schema = "public")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {

  @Id private String email;
  private String username;
  private Profile profile;
  private String provider;
  private boolean tvNow = false;
  private boolean verified = false;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof User)) {
      return false;
    }
    User other = (User) o;
    return email != null && email.equals(other.getEmail());
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
