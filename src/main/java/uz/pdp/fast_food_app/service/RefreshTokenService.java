package uz.pdp.fast_food_app.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import uz.pdp.fast_food_app.model.RefreshToken;
import uz.pdp.fast_food_app.repository.RefreshTokenRepo;


import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {
    @Value("${refreshTokenDurationMs}")
    private Long refreshTokenDurationMs;

    private final RefreshTokenRepo refreshTokenRepo;

    public RefreshTokenService(RefreshTokenRepo refreshTokenRepo) {
        this.refreshTokenRepo = refreshTokenRepo;
    }

    public RefreshToken createRefreshToken(String email) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setEmail(email);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        return refreshTokenRepo.save(refreshToken);
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepo.findByToken(token);
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepo.delete(token);
            throw new RuntimeException("Refresh token was expired. Please make a new login request");
        }
        return token;
    }

    public void deleteByToken(String token) {
        refreshTokenRepo.deleteByToken(token);
    }
}
