package xyz.wirth.bbb.domain.logic;

import lombok.NonNull;
import org.jboss.logging.Logger;
import xyz.wirth.bbb.domain.model.Episode;
import xyz.wirth.bbb.repositories.EpisodeRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;

@ApplicationScoped
public class EpisodeService {

  private final Logger LOG = Logger.getLogger(getClass().getSimpleName());

  private final EpisodeRepository episodeRepository;

  public EpisodeService(EpisodeRepository episodeRepository) {
    this.episodeRepository = episodeRepository;
  }

  public List<Episode> listAllEpisodes() {
    return episodeRepository.listAll();
  }

  public Episode getUpcomingEpsiode() {
    Instant now = Instant.now();
    return episodeRepository.listAll().stream()
        .filter(it -> it.getDate().isBefore(now))
        .min(Comparator.comparing(Episode::getDate))
        .orElseThrow(() -> new NotFoundException("No episode in the future was found"));
  }

  @Transactional
  public void deleteEpisode(@NonNull Long id) {
    var episode = episodeRepository.findByIdOptional(id);
    episode.ifPresent(episodeRepository::delete);
  }

  @Transactional
  public Episode createOrUpdateEpisode(Episode episode) {
    try {
      var existingEpisode = episodeRepository.findByIdOptional(episode.getId());
      if (existingEpisode.isPresent()) {

        episodeRepository.persist(existingEpisode.get());
        LOG.infov("Updated Episode entry {0}", existingEpisode.get());
        return existingEpisode.get();
      } else {
        episodeRepository.persist(episode);
        LOG.infov("Created new Episode entry {0}", episode);
        return episode;
      }
    } catch (PersistenceException pe) {
      LOG.error("Failed to create/update the Episode", pe);
      return null; // TODO exception
    }
  }
}
