package uz.pdp.fast_food_app.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.fast_food_app.model.Rating;
import uz.pdp.fast_food_app.service.RatingService;
import uz.pdp.fast_food_app.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/user/rating")
public class RatingController {

    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PreAuthorize("hasAuthority('ROLE_RATING')")
    @PostMapping("/rating_add")
    public Rating addRating(@RequestParam int score, @RequestParam String review,
                            @RequestParam String restaurantId, @RequestParam String userId) {
        return ratingService.addRating(score, review, restaurantId);
    }

    @PreAuthorize("hasAuthority('ROLE_RATING')")
    @GetMapping("/getRatingsForRestaurant/{id}")
    public List<Rating> getRatingsForRestaurant(@PathVariable String id) {
        return ratingService.getRatingsForRestaurant(id);
    }
}
