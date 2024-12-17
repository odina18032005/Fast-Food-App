package uz.pdp.fast_food_app.dto;

public record AuthenticationDTO(
        String accessToken,
        String refreshToken,
        String error
) {
    public AuthenticationDTO(String accessToken, String refreshToken) {
        this(accessToken, refreshToken, null);
    }
    public AuthenticationDTO(String error) {
        this(null, null, error);
    }
}
