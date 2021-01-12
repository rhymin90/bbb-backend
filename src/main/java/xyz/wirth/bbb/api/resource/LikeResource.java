package xyz.wirth.bbb.api.resource;

import io.quarkus.security.Authenticated;
import org.jboss.logging.Logger;
import xyz.wirth.bbb.api.dto.LikeDto;
import xyz.wirth.bbb.api.mapper.LikeMapper;
import xyz.wirth.bbb.domain.logic.LikeService;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/likes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Authenticated
public class LikeResource {

  private final Logger LOG = Logger.getLogger(LikeResource.class.getSimpleName());

  private final LikeMapper likeMapper;
  private final LikeService likeService;

  public LikeResource(LikeMapper likeMapper, LikeService likeService) {
    this.likeMapper = likeMapper;
    this.likeService = likeService;
  }

  @GET
  public List<LikeDto> list() {
    LOG.debug("listLikes()");
    var likes = likeService.listLikes();

    // likes.forEach(like -> LOG.infov("Like: {0}", like));

    return likes.stream().map(likeMapper::map).collect(Collectors.toList());
  }

  @DELETE
  @Path("/delete/{id}")
  public Response delete(@PathParam("id") Long id) {
    LOG.infov("Delete like entry with id: {0}", id);
    likeService.deleteLike(id);
    return Response.ok().build(); // TODO deleted response
  }

  @POST
  public Response add(@Valid LikeDto likeDto) {
    LOG.infov("Add like from DTO: {0}", likeDto.toString());
    var like = likeMapper.map(likeDto);
    var persistedLike = likeService.createLike(like);
    if (persistedLike == null) {
      return Response.serverError()
          .entity("{'error':'Could not create like'}")
          .build(); // TODO error message
    } else {
      return Response.accepted().entity(likeMapper.map(persistedLike)).build();
    }
  }
}
