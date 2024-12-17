package uz.pdp.fast_food_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.fast_food_app.model.Product;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product, String> {
    List<Product> findByNameContainingIgnoreCase(String name);
}
