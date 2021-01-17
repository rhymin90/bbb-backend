package xyz.wirth.bbb.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import xyz.wirth.bbb.api.dto.FinalistsDto;
import xyz.wirth.bbb.domain.model.Finalists;

@Mapper(componentModel = "cdi", unmappedTargetPolicy = ReportingPolicy.ERROR)
public abstract class FinalistsMapper {

  public abstract Finalists map(FinalistsDto finalistsDto);

  public abstract FinalistsDto map(Finalists finalists);
}
