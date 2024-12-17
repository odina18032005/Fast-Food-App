package uz.pdp.fast_food_app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import uz.pdp.fast_food_app.dto.UserDTO;
import uz.pdp.fast_food_app.model.User;

@Mapper
public interface UserMapper {
    UserMapper USER_MAPPER = Mappers.getMapper(UserMapper.class);

    UserDTO toDTO(User user);
    User toEntity(UserDTO userDTO);
}
