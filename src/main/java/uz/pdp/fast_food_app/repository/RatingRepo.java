package uz.pdp.fast_food_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.fast_food_app.model.Rating;

import java.util.List;

public interface RatingRepo extends JpaRepository<Rating, String> {
    // Find ratings by restaurant
    List<Rating> findByRestaurantId(String restaurantId);

    // Find ratings by user
    List<Rating> findByUserId(String userId);

    // Find ratings by score (can be used for filtering)
    List<Rating> findByScore(int score);
}
