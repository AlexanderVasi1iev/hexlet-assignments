package exercise.mapper;

// BEGIN
import exercise.dto.ProductCreateDTO;
import exercise.dto.ProductDTO;
import exercise.dto.ProductUpdateDTO;
import exercise.model.Product;
import org.mapstruct.*;

@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class ProductMapper {
  //  @Mapping(target = "name", source = "title")
  @InheritConfiguration
  public abstract void create(ProductCreateDTO dto, @MappingTarget Product model);

    @Mapping(target = "cost", source = "price")
    @Mapping(target = "barcode", source =  "vendorCode")
    @Mapping(target = "name", source = "title")
    public abstract Product map(ProductCreateDTO dto);

    // Наследует конфигурацию оригинального метода map
    // Нам не нужно копировать @Mapping(target = "title", source = "name")
    @InheritConfiguration
    public abstract void update(ProductUpdateDTO dto, @MappingTarget Product model);
  @Mapping(target = "cost", source = "price")
  public abstract Product map(ProductUpdateDTO dto);
    // Здесь можно применить обратное наследование
    // @InheritInverseConfiguration
   @Mapping(target = "title", source = "name")
   @Mapping(target = "price", source = "cost")
   @Mapping(target = "vendorCode", source = "barcode")
    public abstract ProductDTO map(Product model);
}
// END
