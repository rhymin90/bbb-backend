package xyz.wirth.bbb.domain.logic;

import lombok.NonNull;
import org.jboss.logging.Logger;
import xyz.wirth.bbb.domain.model.Dismissed;
import xyz.wirth.bbb.repositories.DismissedRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class DismissedService {

  private final Logger LOG = Logger.getLogger(getClass().getSimpleName());

  private final DismissedRepository dismissedRepository;

  public DismissedService(DismissedRepository dismissedRepository) {
    this.dismissedRepository = dismissedRepository;
  }

  public List<Dismissed> listAllDismisseds() {
    return dismissedRepository.listAll();
  }

  public List<Dismissed> listDismisseds(String userId) {
    return dismissedRepository.listAll().stream()
        .filter(it -> it.getUserId().equals(userId))
        .collect(Collectors.toList());
  }

  public boolean hasDismissed(@NonNull Long id) {
    return dismissedRepository.findById(id) != null;
  }

  @Transactional
  public void deleteDismissed(@NonNull Long id, String userId) {
    var dismissed = dismissedRepository.findByIdOptional(id);
    if (dismissed.isPresent() && dismissed.get().getUserId().equals(userId)) {
      dismissedRepository.deleteById(id);
    }
  }

  @Transactional
  public Dismissed createDismissed(Dismissed dismissed) {
    try {
      dismissedRepository.persist(dismissed);
      LOG.infov("Created new Dismissed entry {0}", dismissed.toString());
      return dismissed;
    } catch (PersistenceException pe) {
      LOG.error("Failed to create the Dismissed vote", pe);
      return null; // TODO exception
    }
  }
}
