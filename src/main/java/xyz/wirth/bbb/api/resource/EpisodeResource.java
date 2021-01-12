package xyz.wirth.bbb.api.resource;

import io.quarkus.security.Authenticated;
import xyz.wirth.bbb.api.dto.EpisodeDto;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/episodes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Authenticated
public class EpisodeResource {

  @GET
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
