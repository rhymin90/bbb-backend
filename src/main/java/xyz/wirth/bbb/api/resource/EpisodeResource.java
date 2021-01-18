package xyz.wirth.bbb.api.resource;

import io.quarkus.security.Authenticated;
import org.jboss.logging.Logger;
import xyz.wirth.bbb.api.dto.EpisodeDto;
import xyz.wirth.bbb.api.mapper.EpisodeMapper;
import xyz.wirth.bbb.api.security.AuthenticationFacade;
import xyz.wirth.bbb.domain.logic.EpisodeService;
import xyz.wirth.bbb.domain.model.Profile;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.List;
import java.util.stream.Collectors;

@Path("/episodes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Authenticated
public class EpisodeResource {

  private final Logger LOG = Logger.getLogger(getClass().getSimpleName());

  private final AuthenticationFacade authenticationFacade;
  private final EpisodeMapper episodeMapper;
  private final EpisodeService episodeService;

  public EpisodeResource(
      AuthenticationFacade authenticationFacade,
      EpisodeMapper episodeMapper,
      EpisodeService episodeService) {
    this.authenticationFacade = authenticationFacade;
    this.episodeMapper = episodeMapper;
    this.episodeService = episodeService;
  }

  @GET
  @Path("/upcoming")
  public EpisodeDto upcoming() {
    return episodeMapper.map(episodeService.getUpcomingEpsiode());
  }

  @GET
  public List<EpisodeDto> listEpisodes() {
    LOG.debugv("listEpisodes()");
    return episodeService.listAllEpisodes().stream()
        .map(episodeMapper::map)
        .collect(Collectors.toList());
  }

  @POST
  public Response addOrUpdate(
      @Context SecurityContext securityContext, @Valid EpisodeDto episodeDto) {
    LOG.infov("Add or update episode from DTO: {0}", episodeDto.toString());
    authenticationFacade.hasNeededRights(securityContext, Profile.ADMIN);

    var episode = episodeMapper.map(episodeDto);
    var persistedEpisode = episodeService.createOrUpdateEpisode(episode);
    if (persistedEpisode == null) {
      return Response.serverError()
          .entity("{'error':'Could not create episode'}")
          .build(); // TODO error message
    } else {
      return Response.accepted().entity(episodeMapper.map(persistedEpisode)).build();
    }
  }
}
