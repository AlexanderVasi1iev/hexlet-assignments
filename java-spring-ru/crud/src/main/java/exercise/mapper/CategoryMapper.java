package exercise.mapper;

import exercise.dto.CategoryCreateDTO;
import exercise.dto.CategoryDTO;
import exercise.model.Category;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

// BEGIN
@Mapper(
        uses = {JsonNullableMapper.class, ReferenceMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class CategoryMapper {

   // @Mapping(target = "passwordDigest", source = "password")
    public abstract Category map(CategoryCreateDTO model);

   // @Mapping(target = "username", source = "email")
     public abstract CategoryDTO map(Category model);

   // public abstract void update(CategoryUpdateDTO update, @MappingTarget User destination);


}
// END
