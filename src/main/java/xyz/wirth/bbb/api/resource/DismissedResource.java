package xyz.wirth.bbb.api.resource;

import io.quarkus.security.Authenticated;
import org.jboss.logging.Logger;
import xyz.wirth.bbb.api.dto.DismissedDto;
import xyz.wirth.bbb.api.mapper.DismissedMapper;
import xyz.wirth.bbb.domain.logic.DismissedService;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/dismissed")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Authenticated
public class DismissedResource {

  private final Logger LOG = Logger.getLogger(DismissedResource.class.getSimpleName());

  private final DismissedMapper dismissedMapper;
  private final DismissedService dismissedService;

  public DismissedResource(DismissedMapper dismissedMapper, DismissedService dismissedService) {
    this.dismissedMapper = dismissedMapper;
    this.dismissedService = dismissedService;
  }

  @GET
  public List<DismissedDto> list() {
    LOG.debug("listDismisseds()");
    var dismisseds = dismissedService.listDismisseds();

    // dismisseds.forEach(dismissed -> LOG.infov("Dismissed: {0}", dismissed));

    return dismisseds.stream().map(dismissedMapper::map).collect(Collectors.toList());
  }

  @DELETE
  @Path("/delete/{id}")
  public Response delete(@PathParam("id") Long id) {
    LOG.infov("Delete dismissed entry with id: {0}", id);
    dismissedService.deleteDismissed(id);
    return Response.ok().build(); // TODO deleted response
  }

  @POST
  public Response add(@Valid DismissedDto dismissedDto) {
    LOG.infov("Add dismissed from DTO: {0}", dismissedDto.toString());
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
