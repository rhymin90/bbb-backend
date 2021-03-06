package xyz.wirth.bbb.api.resource;

import io.quarkus.security.Authenticated;
import io.smallrye.jwt.auth.principal.DefaultJWTCallerPrincipal;
import org.jboss.logging.Logger;
import xyz.wirth.bbb.api.dto.UserDto;
import xyz.wirth.bbb.api.dto.UserEmailDto;
import xyz.wirth.bbb.api.mapper.UserMapper;
import xyz.wirth.bbb.api.security.AuthenticationFacade;
import xyz.wirth.bbb.domain.logic.UserService;
import xyz.wirth.bbb.domain.model.Profile;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Authenticated
public class UserResource {

  private final Logger LOG = Logger.getLogger(getClass().getSimpleName());

  private final AuthenticationFacade authenticationFacade;
  private final UserMapper userMapper;
  private final UserService userService;

  public UserResource(
      AuthenticationFacade authenticationFacade, UserMapper userMapper, UserService userService) {
    this.authenticationFacade = authenticationFacade;
    this.userMapper = userMapper;
    this.userService = userService;
  }

  @GET
  public List<UserDto> list(@Context SecurityContext securityContext) {
    LOG.debug("listUsers()");
    authenticationFacade.hasNeededRights(securityContext, Profile.ADMIN);
    return userService.listUsers().stream().map(userMapper::map).collect(Collectors.toList());
  }

  @GET
  @Path("/me")
  public UserDto getUserInfo(@Context SecurityContext securityContext) {
    final var email = authenticationFacade.getRequesterUserId(securityContext);
    var user = userService.getUserByEmail(email);
    return userMapper.map(user);
  }

  @GET
  @Path("/exists/{email}")
  @PermitAll
  public UserEmailDto userExists(
      @Context SecurityContext securityContext, @PathParam("email") String email) {
    var provider = userService.getUserProvider(email);
    return UserEmailDto.builder()
        .email(email)
        .provider(provider.orElse("unknown"))
        .exists(provider.isPresent())
        .build();
  }

  @GET
  @Path("/id/{email}")
  public Response getUser(
      @Context SecurityContext securityContext, @PathParam("email") String email) {
    final var userPrincipal = (DefaultJWTCallerPrincipal) securityContext.getUserPrincipal();
    final var claimEmail = (String) userPrincipal.getClaim("email");

    if (!email.equals(claimEmail)) {
      LOG.warnv(
          "Get data for user with email {0} not matching claim email: {1}", email, claimEmail);
      return Response.status(Response.Status.FORBIDDEN)
          .entity("{'error':'You are not allowed to request data of another user'}")
          .build(); // TODO error message
    }
    var user = userService.getUserByEmail(email);

    if (user == null) {
      return Response.status(Response.Status.NOT_FOUND).build();
    } else {
      LOG.debugv("Found data for user with email {0}: {1}", email, user);
      return Response.ok().entity(userMapper.map(user)).build();
    }
  }

  @POST
  @Path("/id/{email}")
  public Response editUser(
      @Context SecurityContext securityContext,
      @PathParam("email") String email,
      @Valid UserDto userDto) {

    final var userPrincipal = (DefaultJWTCallerPrincipal) securityContext.getUserPrincipal();
    final var claimEmail = (String) userPrincipal.getClaim("email");
    LOG.debugv("Update from {0} for user: {2}", claimEmail, userDto);

    var isAdmin = false;
    if (!email.equals(claimEmail)) {
      // Updating data of users is only allowed as admin
      isAdmin = authenticationFacade.hasNeededRights(securityContext, Profile.ADMIN);
    }

    var user = userService.updateUser(userMapper.map(userDto), isAdmin);

    if (user == null) {
      return Response.status(Response.Status.NOT_FOUND).build();
    } else {
      LOG.infov("Updated data for user with email {0}: {1}", email, user);
      return Response.ok().entity(userMapper.map(user)).build();
    }
  }

  @POST
  public Response add(@Valid UserDto userDto) {
    LOG.infov("Add user from DTO: {0}", userDto.toString());
    var user = userMapper.map(userDto);
    if (user.getProfile().equals(Profile.ADMIN)) {
      return Response.status(Response.Status.FORBIDDEN).build();
    }
    var persistedUser = userService.createUser(user);
    if (persistedUser == null) {
      return Response.status(Response.Status.BAD_REQUEST)
          .entity("{'error':'User already exists'}")
          .build(); // TODO error message
    } else {
      return Response.created(URI.create("/users/" + persistedUser.getEmail()))
          .entity(userMapper.map(persistedUser))
          .build();
    }
  }
}
