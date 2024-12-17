package uz.pdp.fast_food_app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import uz.pdp.fast_food_app.dto.ProductDTO;
import uz.pdp.fast_food_app.model.Product;

@Mapper
public interface ProductMapper {
    ProductMapper PRODUCT_MAPPER = Mappers.getMapper(ProductMapper.class);

    ProductDTO toDTO(Product product);
    Product toEntity(ProductDTO productDTO);
}
