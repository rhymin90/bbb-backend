package xyz.wirth.bbb.domain.logic;

import org.jboss.logging.Logger;
import xyz.wirth.bbb.domain.model.Candidate;
import xyz.wirth.bbb.repositories.CandidateRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class CandidateService {

  private final Logger LOG = Logger.getLogger(getClass().getSimpleName());

  private final CandidateRepository candidateRepository;

  public CandidateService(CandidateRepository candidateRepository) {
    this.candidateRepository = candidateRepository;
  }

  public List<Candidate> listCandidates() {
    return candidateRepository.listAll();
  }

  @Transactional
  public Candidate createCandidate(Candidate candidate) {
    try {
      candidateRepository.persist(candidate);
      LOG.infov("Created new Candidate {0}", candidate.toString());
      return candidate;
    } catch (PersistenceException pe) {
      LOG.error("Failed to create the candidate", pe);
      return null; // TODO exception
    }
  }
}
