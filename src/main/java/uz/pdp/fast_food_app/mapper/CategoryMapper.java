package uz.pdp.fast_food_app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import uz.pdp.fast_food_app.dto.CategoryDTO;
import uz.pdp.fast_food_app.model.Category;

@Mapper
public interface CategoryMapper {
    CategoryMapper CATEGORY_MAPPER = Mappers.getMapper(CategoryMapper.class);

    CategoryDTO toDTO(Category category);
    Category toEntity(CategoryDTO categoryDTO);
}
