package xyz.wirth.bbb.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import xyz.wirth.bbb.api.dto.CandidateDto;
import xyz.wirth.bbb.domain.model.Candidate;

@Mapper(componentModel = "cdi", unmappedTargetPolicy = ReportingPolicy.ERROR)
public abstract class CandidateMapper {

  public abstract Candidate map(CandidateDto candidateDto);

  public abstract CandidateDto map(Candidate candidate);
}
