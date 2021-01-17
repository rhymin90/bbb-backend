package xyz.wirth.bbb.domain.logic;

import org.jboss.logging.Logger;
import xyz.wirth.bbb.domain.model.Finalists;
import xyz.wirth.bbb.repositories.FinalistsRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;

@ApplicationScoped
public class FinalistsService {

  private final Logger LOG = Logger.getLogger(getClass().getSimpleName());

  private final FinalistsRepository finalistsRepository;

  public FinalistsService(FinalistsRepository finalistsRepository) {
    this.finalistsRepository = finalistsRepository;
  }

  public Finalists getFinalists(String userId) {
    return finalistsRepository.listAll().stream()
        .filter(it -> it.getUserId().equals(userId))
        .findFirst()
        .orElse(Finalists.builder().userId(userId).winner(0).second(0).build());
  }

  @Transactional
  public void deleteFinalists(String userId) {
    var finalists = finalistsRepository.findByUserId(userId);
    if (finalists.isPresent() && finalists.get().getUserId().equals(userId)) {
      finalistsRepository.delete(finalists.get());
    }
  }

  @Transactional
  public Finalists createFinalists(Finalists finalists) {

    try {
      var existingFinalists = finalistsRepository.findByUserId(finalists.getUserId());
      if (existingFinalists.isPresent()) {
        existingFinalists.get().setWinner(finalists.getWinner());
        existingFinalists.get().setSecond(finalists.getSecond());
        finalistsRepository.persist(existingFinalists.get());
        LOG.infov("Created new Finalists entry {0}", existingFinalists.get());
        return existingFinalists.get();
      } else {
        finalistsRepository.persist(finalists);
        LOG.infov("Created new Finalists entry {0}", finalists);
        return finalists;
      }
    } catch (PersistenceException pe) {
      LOG.error("Failed to create the Finalists vote", pe);
      return null; // TODO exception
    }
  }
}
