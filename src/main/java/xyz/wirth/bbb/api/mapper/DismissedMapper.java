package xyz.wirth.bbb.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import xyz.wirth.bbb.api.dto.DismissedDto;
import xyz.wirth.bbb.domain.model.Dismissed;

@Mapper(componentModel = "cdi", unmappedTargetPolicy = ReportingPolicy.ERROR)
public abstract class DismissedMapper {

  public abstract Dismissed map(DismissedDto dismissedDto);

  public abstract DismissedDto map(Dismissed dismissed);
}
