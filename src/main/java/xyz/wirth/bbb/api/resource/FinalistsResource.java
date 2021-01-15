package xyz.wirth.bbb.api.resource;

import io.quarkus.security.Authenticated;
import io.smallrye.jwt.auth.principal.DefaultJWTCallerPrincipal;
import org.jboss.logging.Logger;
import xyz.wirth.bbb.api.dto.FinalistsDto;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.Collections;
import java.util.List;

@Path("/finalists")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Authenticated
public class FinalistsResource {

  private final Logger LOG = Logger.getLogger(FinalistsResource.class.getSimpleName());

  // private final AuthenticationFacade authenticationFacade;
  // private final FinalistsMapper finalistsMapper;
  // private final FinalistsService finalistsService;

  /*
  public FinalistsResource(
      AuthenticationFacade authenticationFacade, FinalistsMapper finalistsMapper, FinalistsService finalistsService) {
    this.authenticationFacade = authenticationFacade;
    this.finalistsMapper = finalistsMapper;
    this.finalistsService = finalistsService;
  }
  */

  @GET
  public List<FinalistsDto> list() {
    LOG.debug("listFinalists()");
    // var finalistss = finalistsService.listFinalistss();

    // finalistss.forEach(finalists -> LOG.infov("Finalists: {0}", finalists));

    // return finalistss.stream().map(finalistsMapper::map).collect(Collectors.toList());
    return Collections.emptyList();
  }

  @POST
  public Response addOrUpdate(
      @Context SecurityContext securityContext, @Valid FinalistsDto finalistsDto) {
    LOG.infov("Add or update finalists from DTO: {0}", finalistsDto.toString());
    final var userPrincipal = (DefaultJWTCallerPrincipal) securityContext.getUserPrincipal();
    final var email = (String) userPrincipal.getClaim("email");
    if (!email.equals(finalistsDto.getUserId())) {
      return Response.status(Response.Status.FORBIDDEN).build();
    }

    return Response.accepted().build();
    /*
    var finalists = finalistsMapper.map(finalistsDto);
    var persistedFinalists = finalistsService.createFinalists(finalists);
    if (persistedFinalists == null) {
      return Response.serverError()
          .entity("{'error':'Could not create finalists'}")
          .build(); // TODO error message
    } else {
      return Response.accepted().entity(finalistsMapper.map(persistedFinalists)).build();
    }
    */
  }
}
