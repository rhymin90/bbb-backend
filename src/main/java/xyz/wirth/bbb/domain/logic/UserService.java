package xyz.wirth.bbb.domain.logic;

import lombok.NonNull;
import org.jboss.logging.Logger;
import xyz.wirth.bbb.api.resource.UserResource;
import xyz.wirth.bbb.domain.model.User;
import xyz.wirth.bbb.repositories.UserRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class UserService {

  private final Logger LOG = Logger.getLogger(UserResource.class.getSimpleName());

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public List<User> listUsers() {
    return userRepository.listAll();
  }

  public User getUserByEmail(@NonNull String email) {
    return userRepository.findByEmail(email);
  }

  @Transactional
  public User createUser(@NonNull User user) {

    if (getUserByEmail(user.getEmail()) != null) {
      LOG.warnv("User with email {0} already exists", user.getEmail());
      // TODO exception
      return null;
    }

    try {
      userRepository.persist(user);
      LOG.infov("Created new user {0}", user.toString());
      // return Response.created(URI.create("/users/" + user.getId())).build();
      return user;
    } catch (PersistenceException pe) {
      LOG.error("Unable to create the parameter", pe);
      // return Response.status(Response.Status.BAD_REQUEST).entity(pe.getMessage()).build();
      return null; // TODO exception
    }
  }

  @Transactional
  public User updateUser(@NonNull User user) {

    var existingUser = getUserByEmail(user.getEmail());
    if (existingUser == null) {
      LOG.warnv("User with email {0} does not exist in database", user.getEmail());
      // TODO exception
      return null;
    }

    // TODO extend
    boolean updateNeeded = false;

    if (user.getProfile() != null && existingUser.getProfile() != user.getProfile()) {
      existingUser.setProfile(user.getProfile());
      updateNeeded = true;
    }
    if (!existingUser.getProvider().equals(user.getProvider())) {
      existingUser.setProvider(user.getProvider());
      updateNeeded = true;
    }

    try {
      if (updateNeeded) {
        userRepository.persist(existingUser);
        LOG.infov("Updated user {0} in database", existingUser.toString());
      }

      return existingUser;
    } catch (PersistenceException pe) {
      LOG.error("Unable to create the parameter", pe);
      // return Response.status(Response.Status.BAD_REQUEST).entity(pe.getMessage()).build();
      return null; // TODO exception
    }
  }

  public Optional<String> getUserProvider(String email) {
    var existingUser = Optional.ofNullable(getUserByEmail(email));
    return existingUser.map(User::getProvider);
  }
}
