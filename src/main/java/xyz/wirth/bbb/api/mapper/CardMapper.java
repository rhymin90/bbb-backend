package xyz.wirth.bbb.api.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import xyz.wirth.bbb.api.dto.CardDto;
import xyz.wirth.bbb.domain.model.Card;

@Mapper(componentModel = "cdi", unmappedTargetPolicy = ReportingPolicy.ERROR)
public abstract class CardMapper {

  @Mapping(target = "content", source = "text")
  public abstract Card map(CardDto cardDto);

  @InheritInverseConfiguration
  @Mapping(target = "likes", ignore = true, defaultExpression = "java( Collections.emptyList() )")
  public abstract CardDto map(Card card);
}
