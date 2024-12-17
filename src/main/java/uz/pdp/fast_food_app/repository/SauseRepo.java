package uz.pdp.fast_food_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.fast_food_app.model.Restaurant;
import uz.pdp.fast_food_app.model.Sause;

import java.util.List;
import java.util.Optional;

public interface SauseRepo extends JpaRepository<Sause, String> {
    Sause findByNameAndRestaurant(String name, Restaurant restaurant);

    List<Sause> findByRestaurant(Restaurant restaurantById);
}
