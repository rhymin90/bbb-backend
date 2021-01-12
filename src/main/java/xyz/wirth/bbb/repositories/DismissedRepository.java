package xyz.wirth.bbb.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import xyz.wirth.bbb.domain.model.Dismissed;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DismissedRepository implements PanacheRepository<Dismissed> {

  // put your custom logic here as instance methods

}
