package uz.pdp.fast_food_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.fast_food_app.model.Payment;
import uz.pdp.fast_food_app.model.User;

public interface PaymentRepo extends JpaRepository<Payment, String> {
    Payment findTopByUserOrderByIdDesc(User user);
}
