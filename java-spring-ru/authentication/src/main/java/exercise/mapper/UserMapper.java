package exercise.mapper;

import exercise.dto.UserCreateDTO;
import exercise.dto.UserDTO;
import exercise.model.User;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(uses = {JsonNullableMapper.class, ReferenceMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class UserMapper {

    @Autowired
    private PasswordEncoder passwordEncoder;



  // @Mapping(target = "email", source = "name")
 // @Mapping(source = "password", target = "passwordDigest")
  @Mapping(target = "password", ignore = true)
    public abstract User map(UserCreateDTO dto);

   // @Mapping(target = "email", source = "username")
   // @Mapping(target = "name", ignore = false)
    @Mapping(target = "email", source = "username")
   // @Mapping(target = "passwordDigest", ignore = false)
    public abstract UserDTO map(User model);

    @BeforeMapping
    public void encryptPassword(UserCreateDTO data) {
        var password = data.getPasswordDigest();
        data.setPasswordDigest(passwordEncoder.encode(password));
    }
}
