package xyz.wirth.bbb.api.resource;

import io.quarkus.security.Authenticated;
import xyz.wirth.bbb.api.dto.EpisodeDto;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/episodes")
@Authenticated
public class EpisodeResource {

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/upcoming")
  public EpisodeDto upcoming() {
    return EpisodeDto.builder()
        .episode(1)
        .date(System.currentTimeMillis())
        .time("20:15")
        .amountDismissed(3)
        .upcomingEpisode(true)
        .build();
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<EpisodeDto> list() {
    return List.of(
        EpisodeDto.builder()
            .episode(1)
            .date(System.currentTimeMillis())
            .time("20:15")
            .amountDismissed(3)
            .upcomingEpisode(true)
            .build());
  }
}
