package uz.pdp.fast_food_app.dto;

public record ProductDTO(
        String name,
        String description,
        Long price,
        String restaurantId
) {
}
