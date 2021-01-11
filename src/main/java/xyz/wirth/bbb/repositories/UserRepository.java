package xyz.wirth.bbb.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import xyz.wirth.bbb.domain.model.User;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {

  // put your custom logic here as instance methods
  public User findByEmail(String email) {
    return find("email", email).firstResult();
  }
}
