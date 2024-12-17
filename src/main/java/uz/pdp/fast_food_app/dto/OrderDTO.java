package uz.pdp.fast_food_app.dto;

public record OrderDTO(
        String productId,
        Integer quantity,
        String packages,
        String sause,
        String restaurantId) {
}
