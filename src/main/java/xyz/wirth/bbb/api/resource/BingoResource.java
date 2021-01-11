package xyz.wirth.bbb.api.resource;

import io.quarkus.security.Authenticated;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Collections;
import java.util.List;

@Path("/bingos")
@Authenticated
public class BingoResource {

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/{userId}")
  public List<String> get() {
    return Collections.emptyList();
  }
}
