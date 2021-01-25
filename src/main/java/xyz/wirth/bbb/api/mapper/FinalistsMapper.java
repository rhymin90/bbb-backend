package xyz.wirth.bbb.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import xyz.wirth.bbb.api.dto.FinalistsDto;
import xyz.wirth.bbb.domain.model.Finalists;

import java.time.Instant;

@Mapper(componentModel = "cdi", unmappedTargetPolicy = ReportingPolicy.ERROR)
public abstract class FinalistsMapper {

  @Mapping(target = "winnerDate", ignore = true)
  @Mapping(target = "secondDate", ignore = true)
  public abstract Finalists map(FinalistsDto finalistsDto);

  public abstract FinalistsDto map(Finalists finalists);

  public long mapInstantToLong(Instant instant) {
    return instant.toEpochMilli();
  }
}
