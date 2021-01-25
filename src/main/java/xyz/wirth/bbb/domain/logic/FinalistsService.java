package xyz.wirth.bbb.domain.logic;

import org.jboss.logging.Logger;
import xyz.wirth.bbb.domain.model.Finalists;
import xyz.wirth.bbb.repositories.FinalistsRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import java.time.Instant;

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

  /*
  @Transactional
  public void deleteFinalists(String userId) {
    var finalists = finalistsRepository.findByUserId(userId);
    if (finalists.isPresent() && finalists.get().getUserId().equals(userId)) {
      finalistsRepository.delete(finalists.get());
    }
  }
  */

  @Transactional
  public Finalists createOrUpdateFinalists(Finalists finalists) {

    try {
      var existingFinalistsOptional = finalistsRepository.findByUserId(finalists.getUserId());
      if (existingFinalistsOptional.isPresent()) {
        boolean updateNeeded = false;
        var existingFinalists = existingFinalistsOptional.get();

        if (existingFinalists.getWinner() != finalists.getWinner()) {
          existingFinalists.setWinnerDate(Instant.now());
          existingFinalists.setWinner(finalists.getWinner());
          updateNeeded = true;
        }

        if (existingFinalists.getSecond() != finalists.getSecond()) {
          existingFinalists.setSecondDate(Instant.now());
          existingFinalists.setSecond(finalists.getSecond());
          updateNeeded = true;
        }

        if (updateNeeded) {
          finalistsRepository.persist(existingFinalists);
          LOG.infov("Updated Finalists entry {0}", existingFinalists);
        }
        return existingFinalists;
      } else {
        finalists.setWinnerDate(Instant.now());
        finalists.setSecondDate(Instant.now());
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
