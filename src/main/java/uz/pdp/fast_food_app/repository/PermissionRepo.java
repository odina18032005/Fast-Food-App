package uz.pdp.fast_food_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.fast_food_app.model.Permission;

import java.util.Optional;

public interface PermissionRepo extends JpaRepository<Permission, String> {
    Optional<Permission> findByName(String read);
}
