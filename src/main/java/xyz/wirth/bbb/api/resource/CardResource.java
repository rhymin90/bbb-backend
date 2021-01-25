package xyz.wirth.bbb.api.resource;

import io.quarkus.security.Authenticated;
import org.jboss.logging.Logger;
import xyz.wirth.bbb.api.dto.CardDto;
import xyz.wirth.bbb.api.mapper.CardMapper;
import xyz.wirth.bbb.api.security.AuthenticationFacade;
import xyz.wirth.bbb.domain.logic.CardService;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Path("/cards")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Authenticated
public class CardResource {

  private final Logger LOG = Logger.getLogger(getClass().getSimpleName());

  private final AuthenticationFacade authenticationFacade;
  private final CardMapper cardMapper;
  private final CardService cardService;

  public CardResource(
      AuthenticationFacade authenticationFacade, CardMapper cardMapper, CardService cardService) {
    this.authenticationFacade = authenticationFacade;
    this.cardMapper = cardMapper;
    this.cardService = cardService;
  }

  @GET
  public List<CardDto> list() {
    LOG.debug("listCards()");
    var cards = cardService.listCards();
    return cards.stream().map(cardMapper::map).collect(Collectors.toList());
  }

  @DELETE
  @Path("/delete/{id}")
  public Response delete(@Context SecurityContext securityContext, @PathParam("id") Long id) {
    LOG.infov("Delete card with id: {0}", id);
    var email = authenticationFacade.getRequesterUserId(securityContext);
    cardService.deleteCard(id, email);
    return Response.ok().build(); // TODO deleted response
  }

  @POST
  public Response add(@Valid CardDto cardDto) {
    LOG.infov("Add card from DTO: {0}", cardDto.toString());
    var card = cardMapper.map(cardDto);
    var persistedCard = cardService.createCard(card);
    if (persistedCard == null) {
      return Response.serverError()
          .entity("{'error':'Could not create card'}")
          .build(); // TODO error message
    } else {
      return Response.created(URI.create("/cards/" + persistedCard.getId()))
          .entity(cardMapper.map(persistedCard))
          .build();
    }
  }
}
