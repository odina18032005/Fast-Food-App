package uz.pdp.fast_food_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.fast_food_app.model.Category;

import java.util.Optional;

public interface CategoryRepo extends JpaRepository<Category, String> {
    Optional<Category> findByName(String name);
}
