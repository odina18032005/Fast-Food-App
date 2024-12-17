package uz.pdp.fast_food_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.fast_food_app.model.User;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, String> {
        Optional<User> findByEmail(String email);
}
