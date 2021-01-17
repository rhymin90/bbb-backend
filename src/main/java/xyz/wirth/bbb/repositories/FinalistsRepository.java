package xyz.wirth.bbb.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import xyz.wirth.bbb.domain.model.Finalists;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;

@ApplicationScoped
public class FinalistsRepository implements PanacheRepository<Finalists> {

  // put your custom logic here as instance methods
  public Optional<Finalists> findByUserId(String userId) {
    return find("userId", userId).firstResultOptional();
  }
}
