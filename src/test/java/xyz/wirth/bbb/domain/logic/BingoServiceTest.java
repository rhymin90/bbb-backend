package xyz.wirth.bbb.domain.logic;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import xyz.wirth.bbb.domain.model.Card;
import xyz.wirth.bbb.domain.model.Episode;
import xyz.wirth.bbb.domain.model.Like;
import xyz.wirth.bbb.repositories.BingoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BingoServiceTest {

  @Mock EpisodeService episodeService;
  @Mock CardService cardService;
  @Mock LikeService likeService;
  @Mock BingoRepository bingoRepository;

  @Test
  void createBingoForUserUnique() {
    // given
    var bingoService = new BingoService(episodeService, cardService, likeService, bingoRepository);

    var userId = "test@email.com";
    var upcomingEpisode = Episode.builder().id(1L).build();

    var cardsList =
        LongStream.range(0, 25)
            .mapToObj(it -> Card.builder().id(it).userId(userId).content("Text " + it).build())
            .collect(Collectors.toList());

    var likesList = new ArrayList<Like>();
    for (int i = 0; i < 20; i++) {
      likesList.addAll(createLikesForEpisode(i, i));
    }

    // when
    when(episodeService.getUpcomingEpsiode()).thenReturn(upcomingEpisode);
    when(cardService.listCards()).thenReturn(cardsList);
    when(likeService.listLikes()).thenReturn(likesList);

    var existingBingos =
        LongStream.range(0, 10)
            .mapToObj(it -> bingoService.createBingoForUser("user-" + it))
            .collect(Collectors.toList());
    when(bingoRepository.findByEpisode(upcomingEpisode.getId())).thenReturn(existingBingos);

    var bingo = bingoService.createBingoForUser(userId);

    // then
    assertThat(bingo, is(notNullValue()));
    for (var existingBingo : existingBingos) {
      assertThat(bingo.getCards(), is(not(equalTo(existingBingo.getCards()))));
    }
  }

  @Test
  void createBingoForUserNotUnique() {}

  private List<Like> createLikesForEpisode(long number, long cardId) {
    return LongStream.range(0, number)
        .mapToObj(it -> Like.builder().userId("user-" + it).cardId(cardId).build())
        .collect(Collectors.toList());
  }
}
