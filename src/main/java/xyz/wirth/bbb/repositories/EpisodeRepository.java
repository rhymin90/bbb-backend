package xyz.wirth.bbb.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import xyz.wirth.bbb.domain.model.Episode;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EpisodeRepository implements PanacheRepository<Episode> {

  // put your custom logic here as instance methods

}
