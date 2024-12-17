package uz.pdp.fast_food_app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.fast_food_app.model.Restaurant;
import uz.pdp.fast_food_app.repository.RestaurantRepo;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantService {
    private final RestaurantRepo restaurantRepo;

    public List<Restaurant> searchByName(String name) {
        return restaurantRepo.findByNameContainingIgnoreCase(name);
    }

    public List<Restaurant> searchByRating(double rating) {
        return restaurantRepo.findByRatingGreaterThanEqual(rating);
    }

    public List<Restaurant> getAllRestaurants() {
        return restaurantRepo.findAll();
    }

    public Restaurant getRestaurantById(String id) {
        return restaurantRepo.findById(id).orElse(null);
    }

    public Restaurant saveRestaurant(Restaurant restaurant) {
        return restaurantRepo.save(restaurant);
    }

    public void deleteRestaurant(String id) {
        restaurantRepo.deleteById(id);
    }
}
