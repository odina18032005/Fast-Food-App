package uz.pdp.fast_food_app.service;

import org.springframework.stereotype.Service;
import uz.pdp.fast_food_app.model.Favorite;
import uz.pdp.fast_food_app.model.Product;
import uz.pdp.fast_food_app.repository.FavoriteRepo;
import uz.pdp.fast_food_app.repository.ProductRepo;

import java.util.Optional;

@Service
public class FavoriteService {
    private final FavoriteRepo favoriteRepo;
    private final ProductRepo productRepo; // Assuming you have a repository for products

    public FavoriteService(FavoriteRepo favoriteRepo, ProductRepo productRepo) {
        this.favoriteRepo = favoriteRepo;
        this.productRepo = productRepo;
    }

    public String markAsFavorite(String productId) {
        Optional<Product> optionalProduct = productRepo.findById(productId);
        if (optionalProduct.isEmpty()) {
            return "Product not found!";
        }
        Product product = optionalProduct.get();
        Optional<Favorite> existingFavorite = favoriteRepo.findByProduct(product);
        Favorite favorite;
        if (existingFavorite.isPresent()) {
            favorite = existingFavorite.get();
        } else {
            favorite = new Favorite();
            favorite.setProduct(product);
        }
        favorite.setFavorite(true);
        favoriteRepo.save(favorite);
        return "Product marked as favorite!";
    }
}
