package uz.pdp.fast_food_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.fast_food_app.model.Package;
import uz.pdp.fast_food_app.model.Restaurant;

import java.util.List;

public interface PackageRepo extends JpaRepository<Package, String> {
    Package findByNameAndRestaurant(String name, Restaurant restaurant);

    List<Package> findByRestaurant(Restaurant restaurantById);
}
