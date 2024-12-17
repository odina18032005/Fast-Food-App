package uz.pdp.fast_food_app.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rating extends BaseEntity {
    private int score;  // Rating score, e.g., from 1 to 5
    private String review;  // Optional textual review
    private LocalDateTime timestamp;  // Timestamp when the rating was given
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;  // The restaurant being rated
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;  // The user who gave the rating

}
