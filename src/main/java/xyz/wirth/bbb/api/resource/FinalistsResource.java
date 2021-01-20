package xyz.wirth.bbb.api.resource;

import io.quarkus.security.Authenticated;
import org.jboss.logging.Logger;
import xyz.wirth.bbb.api.dto.FinalistsDto;
import xyz.wirth.bbb.api.mapper.FinalistsMapper;
import xyz.wirth.bbb.api.security.AuthenticationFacade;
import xyz.wirth.bbb.domain.logic.FinalistsService;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Path("/finalists")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Authenticated
public class FinalistsResource {

  private final Logger LOG = Logger.getLogger(getClass().getSimpleName());

  private final AuthenticationFacade authenticationFacade;
  private final FinalistsMapper finalistsMapper;
  private final FinalistsService finalistsService;

  public FinalistsResource(
      AuthenticationFacade authenticationFacade,
      FinalistsMapper finalistsMapper,
      FinalistsService finalistsService) {
    this.authenticationFacade = authenticationFacade;
    this.finalistsMapper = finalistsMapper;
    this.finalistsService = finalistsService;
  }

  @GET
  public FinalistsDto get(@Context SecurityContext securityContext) {
    final var userId = authenticationFacade.getRequesterUserId(securityContext);
    LOG.debugv("getFinalists() for {}", userId);
    var finalists = finalistsService.getFinalists(userId);

    return finalistsMapper.map(finalists);
  }

  @POST
  public Response addOrUpdate(
      @Context SecurityContext securityContext, @Valid FinalistsDto finalistsDto) {
    LOG.infov("Add or update finalists from DTO: {0}", finalistsDto.toString());
    authenticationFacade.compareRequesterWithUserId(securityContext, finalistsDto.getUserId());

    var finalists = finalistsMapper.map(finalistsDto);
    var persistedFinalists = finalistsService.createOrUpdateFinalists(finalists);
    if (persistedFinalists == null) {
      return Response.serverError()
          .entity("{'error':'Could not create finalists'}")
          .build(); // TODO error message
    } else {
      return Response.accepted().entity(finalistsMapper.map(persistedFinalists)).build();
    }
  }
}
