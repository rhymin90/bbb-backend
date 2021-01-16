package xyz.wirth.bbb.api.resource;

import org.jboss.logging.Logger;
import xyz.wirth.bbb.api.dto.CandidateDto;
import xyz.wirth.bbb.api.mapper.CandidateMapper;
import xyz.wirth.bbb.domain.logic.CandidateService;

import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

// @Path("/candidates")
// @Produces(MediaType.APPLICATION_JSON)
// @Consumes(MediaType.APPLICATION_JSON)
// @Authenticated
public class CandidateResource {

  private final Logger LOG = Logger.getLogger(getClass().getSimpleName());

  private final CandidateMapper candidateMapper;
  private final CandidateService candidateService;

  public CandidateResource(CandidateMapper candidateMapper, CandidateService candidateService) {
    this.candidateMapper = candidateMapper;
    this.candidateService = candidateService;
  }

  @GET
  public List<CandidateDto> list() {
    LOG.debug("listCandidates()");
    var candidates = candidateService.listCandidates();

    // candidates.forEach(candidate -> LOG.infov("Candidate: {0}", candidate));

    return candidates.stream()
        .filter(it -> it.getId() > 0)
        .map(candidateMapper::map)
        .collect(Collectors.toList());
  }

  @POST
  public Response add(@Valid CandidateDto candidateDto) {
    LOG.infov("Add candidate from DTO: {0}", candidateDto.toString());
    var candidate = candidateMapper.map(candidateDto);
    var persistedCandidate = candidateService.createCandidate(candidate);
    if (persistedCandidate == null) {
      return Response.serverError()
          .entity("{'error':'Could not create candidate'}")
          .build(); // TODO error message
    } else {
      return Response.created(URI.create("/candidates/" + persistedCandidate.getId()))
          .entity(candidateMapper.map(persistedCandidate))
          .build();
    }
  }
}
