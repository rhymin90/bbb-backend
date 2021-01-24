package xyz.wirth.bbb.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import xyz.wirth.bbb.domain.model.Bingo;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class BingoRepository implements PanacheRepository<Bingo> {

  // put your custom logic here as instance methods
  public List<Bingo> findByUserId(String userId) {
    return find("userId", userId).list();
  }

  public List<Bingo> findByEpisode(long episode) {
    return find("episode", episode).list();
  }
}
