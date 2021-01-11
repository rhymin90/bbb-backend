package xyz.wirth.bbb.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import xyz.wirth.bbb.domain.model.Like;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LikeRepository implements PanacheRepository<Like> {

  // put your custom logic here as instance methods

}
