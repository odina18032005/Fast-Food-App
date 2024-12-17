package uz.pdp.fast_food_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.fast_food_app.model.Order;

import java.util.List;

public interface OrderRepo extends JpaRepository<Order, String> {
    List<Order> findAllByUserId(String userId);
}
