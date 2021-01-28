package xyz.wirth.bbb.domain.logic;

import org.jboss.logging.Logger;
import org.mapstruct.ap.internal.util.Strings;
import xyz.wirth.bbb.domain.model.Bingo;
import xyz.wirth.bbb.domain.model.Card;
import xyz.wirth.bbb.domain.model.Like;
import xyz.wirth.bbb.repositories.BingoRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class BingoService {
  private static final int BINGO_SIZE = 16;
  private final Logger LOG = Logger.getLogger(getClass().getSimpleName());

  private final EpisodeService episodeService;
  private final CardService cardService;
  private final LikeService likeService;
  private final BingoRepository bingoRepository;

  public BingoService(
      EpisodeService episodeService,
      CardService cardService,
      LikeService likeService,
      BingoRepository bingoRepository) {
    this.episodeService = episodeService;
    this.cardService = cardService;
    this.likeService = likeService;
    this.bingoRepository = bingoRepository;
  }

  public List<Bingo> listBingosForUser(String userId) {
    return bingoRepository.findByUserId(userId);
  }

  private List<Bingo> listBingosForEpisode(long episode) {
    return bingoRepository.findByEpisode(episode);
  }

  @Transactional
  public Bingo createBingoForUser(String userId) {

    var likesByCard =
        likeService.listLikes().stream()
            .collect(Collectors.groupingBy(Like::getCardId, Collectors.counting()));

    var cards =
        cardService.listCards().stream()
            .filter(it -> likesByCard.containsKey(it.getId()))
            .sorted(
                (card1, card2) ->
                    Long.compare(likesByCard.get(card2.getId()), likesByCard.get(card1.getId())))
            .limit(BINGO_SIZE)
            .collect(Collectors.toList());

    if (cards.size() < BINGO_SIZE) {
      throw new IllegalStateException(
          "Cannot create bingo with less than " + BINGO_SIZE + " cards");
    }

    var upcomingEpisode = episodeService.getUpcomingEpsiode();
    var existingBingosForEpisode = listBingosForEpisode(upcomingEpisode.getId());

    var cardsForBingo = getCardsShuffled(cards);
    while (!isUniqueCardsList(cardsForBingo, existingBingosForEpisode)) {
      LOG.debugv("Need to shuffle again");
      cardsForBingo = getCardsShuffled(cards);
    }

    cardService.setDeletableForCards(cards, false);

    var bingo =
        Bingo.builder()
            .userId(userId)
            .episode(upcomingEpisode.getId())
            .cards(cardsForBingo)
            .build();

    try {
      bingoRepository.persist(bingo);
      LOG.infov("Created new Bingo entry {0}", bingo.toString());
      return bingo;
    } catch (PersistenceException pe) {
      LOG.error("Failed to create the Bingo vote", pe);
      return null; // TODO exception
    }
  }

  private String getCardsShuffled(List<Card> cards) {
    Collections.shuffle(cards);
    return Strings.join(cards.stream().map(Card::getId).collect(Collectors.toList()), ";");
  }

  private boolean isUniqueCardsList(String cardsForBingo, List<Bingo> existingBingos) {
    var isUnique = existingBingos.stream().anyMatch(it -> it.getCards().equals(cardsForBingo));
    if (!isUnique) {
      LOG.debugv(
          "Bingo cards for episode are not unique. Already found same constellation for other user");
    }
    return !isUnique;
  }
}
