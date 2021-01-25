package xyz.wirth.bbb.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import xyz.wirth.bbb.api.dto.BingoDto;
import xyz.wirth.bbb.domain.model.Bingo;

@Mapper(componentModel = "cdi", unmappedTargetPolicy = ReportingPolicy.ERROR)
public abstract class BingoMapper {

  @Mapping(target = "id", ignore = true)
  public abstract Bingo map(BingoDto bingoDto);

  public abstract BingoDto map(Bingo bingo);
}
