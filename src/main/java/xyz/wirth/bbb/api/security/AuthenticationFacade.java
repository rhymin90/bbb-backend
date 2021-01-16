package xyz.wirth.bbb.api.security;

import io.quarkus.security.ForbiddenException;
import io.quarkus.security.UnauthorizedException;
import io.smallrye.jwt.auth.principal.DefaultJWTCallerPrincipal;
import org.jboss.logging.Logger;
import xyz.wirth.bbb.domain.logic.UserService;
import xyz.wirth.bbb.domain.model.Profile;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.SecurityContext;

@ApplicationScoped
public class AuthenticationFacade {

  private final Logger LOG = Logger.getLogger(getClass().getSimpleName());

  private final UserService userService;

  public AuthenticationFacade(UserService userService) {
    this.userService = userService;
  }

  public boolean hasNeededRights(SecurityContext securityContext, Profile neededProfile) {
    final var userPrincipal = (DefaultJWTCallerPrincipal) securityContext.getUserPrincipal();
    if (userPrincipal == null) {
      throw new UnauthorizedException("You are not authorized");
    }
    final var email = (String) userPrincipal.getClaim("email");

    final var user = userService.getUserByEmail(email);
    if (user == null) {
      LOG.warnv("User with email {0} is not in the system", email);
      throw new UnauthorizedException("You are not a user of the system");
    }

    if (!user.getProfile().equals(neededProfile)) {
      LOG.warnv("User {0} does not have the needed profile {1}", user, neededProfile);
      throw new UnauthorizedException("You don't have enough rights for the desired action");
    }
    return true;
  }

  public void compareRequesterWithUserId(SecurityContext securityContext, String userId) {
    final var email = getRequesterUserId(securityContext);
    if (!email.equals(userId)) {
      throw new ForbiddenException("You are not allowed to do that");
    }
  }

  public String getRequesterUserId(SecurityContext securityContext) {
    final var userPrincipal = (DefaultJWTCallerPrincipal) securityContext.getUserPrincipal();
    if (userPrincipal == null) {
      throw new UnauthorizedException("You are not authorized");
    }
    return (String) userPrincipal.getClaim("email");
  }
}
