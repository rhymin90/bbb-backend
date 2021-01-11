package xyz.wirth.bbb.api.resource;

import io.quarkus.security.Authenticated;
import xyz.wirth.bbb.api.dto.DismissedDto;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Collections;
import java.util.List;

@Path("/dismissed")
@Authenticated
public class DismissedResource {

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<DismissedDto> get() {
    return Collections.emptyList();
  }
}
