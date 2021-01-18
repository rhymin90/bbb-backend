package xyz.wirth.bbb.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import xyz.wirth.bbb.api.dto.EpisodeDto;
import xyz.wirth.bbb.domain.model.Episode;

@Mapper(componentModel = "cdi", unmappedTargetPolicy = ReportingPolicy.ERROR)
public abstract class EpisodeMapper {

  public abstract Episode map(EpisodeDto episodeDto);

  public abstract EpisodeDto map(Episode episode);
}
