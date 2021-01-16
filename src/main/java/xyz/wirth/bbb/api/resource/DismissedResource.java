package xyz.wirth.bbb.api.resource;

import io.quarkus.security.Authenticated;
import org.jboss.logging.Logger;
import xyz.wirth.bbb.api.dto.DismissedDto;
import xyz.wirth.bbb.api.mapper.DismissedMapper;
import xyz.wirth.bbb.api.security.AuthenticationFacade;
import xyz.wirth.bbb.domain.logic.DismissedService;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.List;
import java.util.stream.Collectors;

@Path("/dismissed")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Authenticated
public class DismissedResource {

  private final Logger LOG = Logger.getLogger(DismissedResource.class.getSimpleName());

  private final AuthenticationFacade authenticationFacade;
  private final DismissedMapper dismissedMapper;
  private final DismissedService dismissedService;

  public DismissedResource(
      AuthenticationFacade authenticationFacade,
      DismissedMapper dismissedMapper,
      DismissedService dismissedService) {
    this.authenticationFacade = authenticationFacade;
    this.dismissedMapper = dismissedMapper;
    this.dismissedService = dismissedService;
  }

  @GET
  public List<DismissedDto> list(@Context SecurityContext securityContext) {
    LOG.debug("listDismisseds()");
    final var email = authenticationFacade.getRequesterUserId(securityContext);
    var dismisseds = dismissedService.listDismisseds(email);
    return dismisseds.stream().map(dismissedMapper::map).collect(Collectors.toList());
  }

  @DELETE
  @Path("/delete/{id}")
  public Response delete(@Context SecurityContext securityContext, @PathParam("id") Long id) {
    LOG.infov("Delete dismissed entry with id: {0}", id);
    var email = authenticationFacade.getRequesterUserId(securityContext);
    dismissedService.deleteDismissed(id, email);
    return Response.ok().build(); // TODO deleted response
  }

  @POST
  public Response add(@Context SecurityContext securityContext, @Valid DismissedDto dismissedDto) {
    LOG.infov("Add dismissed from DTO: {0}", dismissedDto.toString());
    authenticationFacade.compareRequesterWithUserId(securityContext, dismissedDto.getUserId());

    var dismissed = dismissedMapper.map(dismissedDto);
    var persistedDismissed = dismissedService.createDismissed(dismissed);
    if (persistedDismissed == null) {
      return Response.serverError()
          .entity("{'error':'Could not create dismissed'}")
          .build(); // TODO error message
    } else {
      return Response.accepted().entity(dismissedMapper.map(persistedDismissed)).build();
    }
  }
}
