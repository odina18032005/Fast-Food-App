package uz.pdp.fast_food_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.fast_food_app.model.Restaurant;

import java.util.List;

public interface RestaurantRepo extends JpaRepository<Restaurant, String> {
    // Метод для поиска ресторанов по названию (частичное совпадение)
    List<Restaurant> findByNameContainingIgnoreCase(String name);

    // Метод для поиска ресторанов с рейтингом выше определенного значения
    List<Restaurant> findByRatingGreaterThanEqual(double rating);

    Restaurant findRestaurantById(String restaurantId);
}
