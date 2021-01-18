package xyz.wirth.bbb.api.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import xyz.wirth.bbb.api.dto.EpisodeDto;
import xyz.wirth.bbb.domain.model.Episode;

import java.time.Instant;

@Mapper(componentModel = "cdi", unmappedTargetPolicy = ReportingPolicy.ERROR)
public abstract class EpisodeMapper {

  @Mapping(target = "id", source = "episode")
  public abstract Episode map(EpisodeDto episodeDto);

  @InheritInverseConfiguration
  public abstract EpisodeDto map(Episode episode);

  public long mapInstantToLong(Instant instant) {
    return instant.toEpochMilli();
  }

  public Instant mapLongToInstant(long input) {
    return Instant.ofEpochMilli(input);
  }
}
