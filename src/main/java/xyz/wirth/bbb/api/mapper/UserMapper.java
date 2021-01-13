package xyz.wirth.bbb.api.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import xyz.wirth.bbb.api.dto.UserDto;
import xyz.wirth.bbb.domain.model.Profile;
import xyz.wirth.bbb.domain.model.User;

@Mapper(componentModel = "cdi", unmappedTargetPolicy = ReportingPolicy.ERROR)
public abstract class UserMapper {

  @Mapping(target = "username", source = "name")
  public abstract User map(UserDto userDto);

  @InheritInverseConfiguration
  public abstract UserDto map(User user);

  public Profile mapProfileFromString(String input) {
    if (input == null) {
      return Profile.USER;
    } else {
      return Profile.valueOf(input);
    }
  }
}
