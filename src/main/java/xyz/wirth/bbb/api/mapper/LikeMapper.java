package xyz.wirth.bbb.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import xyz.wirth.bbb.api.dto.LikeDto;
import xyz.wirth.bbb.domain.model.Like;

@Mapper(componentModel = "cdi", unmappedTargetPolicy = ReportingPolicy.ERROR)
public abstract class LikeMapper {

  public abstract Like map(LikeDto likeDto);

  public abstract LikeDto map(Like like);
}
