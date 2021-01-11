package xyz.wirth.bbb.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import xyz.wirth.bbb.domain.model.Candidate;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CandidateRepository implements PanacheRepository<Candidate> {

  // put your custom logic here as instance methods

}
