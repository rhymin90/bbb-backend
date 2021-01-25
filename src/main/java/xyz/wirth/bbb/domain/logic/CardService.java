package xyz.wirth.bbb.domain.logic;

import lombok.NonNull;
import org.jboss.logging.Logger;
import xyz.wirth.bbb.domain.model.Card;
import xyz.wirth.bbb.repositories.CardRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class CardService {

  private final Logger LOG = Logger.getLogger(getClass().getSimpleName());

  private final CardRepository cardRepository;

  public CardService(CardRepository cardRepository) {
    this.cardRepository = cardRepository;
  }

  public List<Card> listCards() {
    return cardRepository.listAll();
  }

  @Transactional
  public Card createCard(Card card) {
    try {
      cardRepository.persist(card);
      LOG.infov("Created new Card {0}", card.toString());
      return card;
    } catch (PersistenceException pe) {
      LOG.error("Failed to create the card", pe);
      return null; // TODO exception
    }
  }

  @Transactional
  public void deleteCard(@NonNull Long id, String userId) {
    var card = cardRepository.findByIdOptional(id);
    if (card.isPresent() && card.get().getUserId().equals(userId)) {
      cardRepository.deleteById(id);
    }
  }
}
