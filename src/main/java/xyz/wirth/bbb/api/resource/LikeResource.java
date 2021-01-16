package xyz.wirth.bbb.api.resource;

import io.quarkus.security.Authenticated;
import org.jboss.logging.Logger;
import xyz.wirth.bbb.api.dto.LikeDto;
import xyz.wirth.bbb.api.mapper.LikeMapper;
import xyz.wirth.bbb.api.security.AuthenticationFacade;
import xyz.wirth.bbb.domain.logic.LikeService;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.List;
import java.util.stream.Collectors;

@Path("/likes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Authenticated
public class LikeResource {

  private final Logger LOG = Logger.getLogger(getClass().getSimpleName());

  private final AuthenticationFacade authenticationFacade;
  private final LikeMapper likeMapper;
  private final LikeService likeService;

  public LikeResource(
      AuthenticationFacade authenticationFacade, LikeMapper likeMapper, LikeService likeService) {
    this.authenticationFacade = authenticationFacade;
    this.likeMapper = likeMapper;
    this.likeService = likeService;
  }

  @GET
  public List<LikeDto> list() {
    LOG.debug("listLikes()");
    var likes = likeService.listLikes();
    return likes.stream().map(likeMapper::map).collect(Collectors.toList());
  }

  @POST
  @Path("/delete")
  public Response delete(@Context SecurityContext securityContext, @Valid LikeDto likeDto) {
    LOG.infov("Delete like: {0}", likeDto);
    authenticationFacade.compareRequesterWithUserId(securityContext, likeDto.getUserId());
    var like = likeMapper.map(likeDto);
    likeService.deleteLike(like);
    return Response.ok().build(); // TODO deleted response
  }

  @POST
  public Response add(@Context SecurityContext securityContext, @Valid LikeDto likeDto) {
    LOG.infov("Add like from DTO: {0}", likeDto.toString());
    authenticationFacade.compareRequesterWithUserId(securityContext, likeDto.getUserId());

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
