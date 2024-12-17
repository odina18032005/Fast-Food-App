package uz.pdp.fast_food_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.fast_food_app.model.Basket;

import java.util.Optional;

public interface BasketRepo extends JpaRepository<Basket, String> {
    Optional<Basket> findByUserId(String userId);
}
