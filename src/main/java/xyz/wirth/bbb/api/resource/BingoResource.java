package xyz.wirth.bbb.api.resource;

import io.quarkus.security.Authenticated;
import org.jboss.logging.Logger;
import xyz.wirth.bbb.api.dto.BingoDto;
import xyz.wirth.bbb.api.mapper.BingoMapper;
import xyz.wirth.bbb.api.security.AuthenticationFacade;
import xyz.wirth.bbb.domain.logic.BingoService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import java.util.List;
import java.util.stream.Collectors;

@Path("/bingos")
@Authenticated
public class BingoResource {
  private final Logger LOG = Logger.getLogger(getClass().getSimpleName());

  private final AuthenticationFacade authenticationFacade;
  private final BingoMapper bingoMapper;
  private final BingoService bingoService;

  public BingoResource(
      AuthenticationFacade authenticationFacade,
      BingoMapper bingoMapper,
      BingoService bingoService) {
    this.authenticationFacade = authenticationFacade;
    this.bingoMapper = bingoMapper;
    this.bingoService = bingoService;
  }

  @GET
  public List<BingoDto> getBingos(@Context SecurityContext securityContext) {
    final var userId = authenticationFacade.getRequesterUserId(securityContext);
    LOG.debugv("getBingos() for {}", userId);

    var bingos = bingoService.listBingosForUser(userId);
    return bingos.stream().map(bingoMapper::map).collect(Collectors.toList());
  }

  @GET
  @Path("/create")
  public BingoDto createBingo(@Context SecurityContext securityContext) {
    final var userId = authenticationFacade.getRequesterUserId(securityContext);
    LOG.debugv("createBingo() for {}", userId);

    var bingo = bingoService.createBingoForUser(userId);
    return bingoMapper.map(bingo);
  }
}
