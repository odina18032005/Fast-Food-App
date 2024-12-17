package uz.pdp.fast_food_app.dto;

public record PackageDTO(
        String name,
        Long price,
        String restaurantId
) {
}
