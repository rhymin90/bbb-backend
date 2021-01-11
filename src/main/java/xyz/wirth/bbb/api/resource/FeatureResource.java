package xyz.wirth.bbb.api.resource;

import io.quarkus.security.Authenticated;
import xyz.wirth.bbb.api.dto.FeatureDto;
import xyz.wirth.bbb.domain.model.Features;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Path("/features")
@Authenticated
public class FeatureResource {

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<FeatureDto> get() {
    return Arrays.stream(Features.values())
        .map(it -> new FeatureDto(it.name(), true))
        .collect(Collectors.toList());
  }
}
