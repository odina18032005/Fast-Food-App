package uz.pdp.fast_food_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.fast_food_app.model.Role;

import java.util.Optional;

public interface RoleRepo extends JpaRepository<Role, String> {
    Optional<Role> findByName(String name);
}
