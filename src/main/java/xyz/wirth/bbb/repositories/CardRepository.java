package xyz.wirth.bbb.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import xyz.wirth.bbb.domain.model.Card;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CardRepository implements PanacheRepository<Card> {

  // put your custom logic here as instance methods

}
