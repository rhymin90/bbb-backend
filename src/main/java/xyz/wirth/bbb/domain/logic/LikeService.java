package xyz.wirth.bbb.domain.logic;

import lombok.NonNull;
import org.jboss.logging.Logger;
import xyz.wirth.bbb.domain.model.Like;
import xyz.wirth.bbb.repositories.LikeRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class LikeService {

  private final Logger LOG = Logger.getLogger(getClass().getSimpleName());

  private final LikeRepository likeRepository;

  public LikeService(LikeRepository likeRepository) {
    this.likeRepository = likeRepository;
  }

  public List<Like> listLikes() {
    return likeRepository.listAll();
  }
  /*
  public boolean existingLike(String userId, Long cardId){
    likeRepository.list()
  }
  */

  @Transactional
  public void deleteLike(@NonNull Like like) {
    likeRepository.delete("userId = ?1 and cardId = ?2", like.getUserId(), like.getCardId());
  }

  @Transactional
  public Like createLike(Like like) {
    try {
      likeRepository.persist(like);
      LOG.infov("Created new Like entry {0}", like.toString());
      return like;
    } catch (PersistenceException pe) {
      LOG.error("Failed to create the Like vote", pe);
      return null; // TODO exception
    }
  }
}
