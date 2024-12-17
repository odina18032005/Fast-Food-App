package uz.pdp.fast_food_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.fast_food_app.model.Favorite;
import uz.pdp.fast_food_app.model.Product;

import java.util.Optional;

public interface FavoriteRepo extends JpaRepository<Favorite, String> {
    Optional<Favorite> findByProduct(Product product);
}
