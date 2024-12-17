package uz.pdp.fast_food_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.fast_food_app.model.RefreshToken;

import java.util.Optional;

@Repository
public interface RefreshTokenRepo extends JpaRepository<RefreshToken, String> {
    Optional<RefreshToken> findByToken(String token);
    void deleteByToken(String token);
}
