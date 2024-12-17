package uz.pdp.fast_food_app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.fast_food_app.model.Rating;
import uz.pdp.fast_food_app.model.Restaurant;
import uz.pdp.fast_food_app.model.User;
import uz.pdp.fast_food_app.repository.RatingRepo;
import uz.pdp.fast_food_app.repository.RestaurantRepo;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RatingService {
    private final UserService userService;
    private final RatingRepo ratingRepo;
    private final RestaurantRepo restaurantRepo;

    // Method to add a new rating
    public Rating addRating(int score, String review, String restaurantId) {
        // Retrieve the associated restaurant and user (you can handle these via your services)
        Restaurant restaurant = restaurantRepo.findRestaurantById(restaurantId);  // Assuming a method to fetch the restaurant
        User user = userService.getCurrentUser();  // Assuming a method to fetch the user

        Rating rating = new Rating(score, review, LocalDateTime.now(), restaurant, user);
        return ratingRepo.save(rating);
    }

    // Method to get ratings for a restaurant
    public List<Rating> getRatingsForRestaurant(String restaurantId) {
        return ratingRepo.findByRestaurantId(restaurantId);
    }
}
