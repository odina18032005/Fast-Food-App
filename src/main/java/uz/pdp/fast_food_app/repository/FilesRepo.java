package uz.pdp.fast_food_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.fast_food_app.model.Files;

import java.util.Optional;

public interface FilesRepo extends JpaRepository<Files, String> {
    Optional<Files> findImageById(String imageId);
}
