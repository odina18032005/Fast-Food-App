package uz.pdp.fast_food_app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.fast_food_app.dto.OrderDTO;
import uz.pdp.fast_food_app.dto.PackageDTO;
import uz.pdp.fast_food_app.dto.ProductDTO;
import uz.pdp.fast_food_app.dto.SauseDTO;
import uz.pdp.fast_food_app.mapper.ProductMapper;
import uz.pdp.fast_food_app.model.*;
import uz.pdp.fast_food_app.model.Package;
import uz.pdp.fast_food_app.repository.*;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepo productRepo;
    private final FilesRepo filesRepo;
    private final FavoriteRepo favoriteRepo;
    private final BasketRepo basketRepo;
    private final PaymentRepo paymentRepo;
    private final OrderRepo orderRepo;
    private final UserService userService;
    private final S3StorageService s3StorageService;
    private final DeliveryService ambulanceService;
    private final String AWS_PUBLIC = "public";
    private final String AWS_URL = "https://medicsg40website.s3.ap-northeast-1.amazonaws.com/";
    private final RestaurantRepo restaurantRepo;
    private final SauseRepo sauseRepo;
    private final PackageRepo packageRepo;

    public String addProduct(ProductDTO productDTO, MultipartFile file) {
        Product product = ProductMapper.PRODUCT_MAPPER.toEntity(productDTO);
        if (file != null && !file.isEmpty()) {
            Files files = s3StorageService.save(file, AWS_PUBLIC);
            files.setUrl(AWS_URL + files.getPath());
            Files savedFile = filesRepo.save(files);
            product.setFile(savedFile);
        } else {
            product.setFile(null);
        }
        productRepo.save(product);
        return "Successfully added product.";
    }

    public String updateProduct(String id, ProductDTO productDTO) {
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        product.setName(productDTO.name());
        product.setPrice(productDTO.price());
        product.setDescription(productDTO.description());
        productRepo.save(product);
        return "Successfully updated product.";
    }

    public String removeProduct(String id) {
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        productRepo.delete(product);
        return "Successfully removed product.";
    }

    public String orderProduct(OrderDTO orderDTO) {
        Product product = productRepo.findById(orderDTO.productId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid product ID"));
        User currentUser = userService.getCurrentUser();
        Basket basket = basketRepo.findByUserId(currentUser.getId())
                .orElseGet(() -> Basket.builder().user(currentUser).orders(new ArrayList<>()).build());
        Restaurant restaurant = restaurantRepo.findRestaurantById(orderDTO.restaurantId());
        Package aPackage = packageRepo.findByNameAndRestaurant(orderDTO.packages(), restaurant);

        if (orderDTO.sause().isEmpty()){
            Sause sause = sauseRepo.findByNameAndRestaurant(orderDTO.sause(), restaurant);
            if (basket.getId() == null) {
                basketRepo.save(basket);
                Order order = Order.builder()
                        .product(product)
                        .user(currentUser)
                        .count(orderDTO.quantity())
                        .basket(basket)
                        .restaurant(restaurant)
                        .aPackage(aPackage)
                        .sause(sause)
                        .build();
                orderRepo.save(order);

                basket.getOrders().add(order);
                basketRepo.save(basket);
                return "Basket updated with product: " + product.getName();
            }
        }

        if (basket.getId() == null) {
            basketRepo.save(basket);
        }

        Order order = Order.builder()
                .product(product)
                .user(currentUser)
                .count(orderDTO.quantity())
                .basket(basket)
                .restaurant(restaurant)
                .aPackage(aPackage)
                .build();
        orderRepo.save(order);

        basket.getOrders().add(order);
        basketRepo.save(basket);
        return "Basket updated with product: " + product.getName();
    }

    public String myCard() {
        User currentUser = userService.getCurrentUser();
        Basket basket = basketRepo.findByUserId(currentUser.getId())
                .orElseThrow(() -> new IllegalArgumentException("No basket found for this user."));

        List<Order> userOrders = basket.getOrders();
        if (userOrders.isEmpty()) {
            return "Your basket is empty. Add some products to view them here.";
        }

        StringBuilder orderDetails = new StringBuilder("Basket - Payment Details:\n\n");


        Double totalPrice = null;

        for (Order order : userOrders) {
            Product product = order.getProduct();
            double itemTotalPrice = product.getPrice() * order.getCount();
            if (order.getSause() != null){
                orderDetails.append("Product: ").append(product.getName()).append("\n")
                        .append("Price per Unit: $").append(product.getPrice()).append("\n")
                        .append("Quantity: ").append(order.getCount()).append("\n")
                        .append("Package: $").append(order.getAPackage().getPrice()).append("\n")
                        .append("Sause: $").append(order.getSause().getPrice()).append("\n")
                        .append("Item Total: $").append(String.format("%.2f", itemTotalPrice)).append("\n\n");
                totalPrice=itemTotalPrice + totalPrice + order.getAPackage().getPrice() + order.getSause().getPrice();
            }
            orderDetails.append("Product: ").append(product.getName()).append("\n")
                    .append("Price per Unit: $").append(product.getPrice()).append("\n")
                    .append("Quantity: ").append(order.getCount()).append("\n")
                    .append("Package: $").append(String.format("%.2f", order.getAPackage().getPrice())).append("\n\n")
                    .append("Item Total: $").append(String.format("%.2f", itemTotalPrice)).append("\n\n");
            totalPrice=itemTotalPrice + totalPrice + order.getAPackage().getPrice();
        }

        orderDetails.append("Total: $").append(String.format("%.2f", totalPrice)).append("\n");
        Payment payment = Payment.builder()
                .totals(totalPrice)
                .user(currentUser)
                .build();
        paymentRepo.save(payment);
        return orderDetails.append("\nPlease enter your card number to complete the purchase.").toString();
    }

    public String buy(String cardNumber) {
        User currentUser = userService.getCurrentUser();
        Basket basket = basketRepo.findByUserId(currentUser.getId())
                .orElseThrow(() -> new IllegalArgumentException("No basket found for this user."));
        if (cardNumber == null || cardNumber.length() != 16) {
            return "Invalid card number. Please enter a valid 16-digit card number.";
        }
        Payment payment = paymentRepo.findTopByUserOrderByIdDesc(currentUser);
        if (payment == null) {
            return "No payment details found. Please add items to your basket first.";
        }
        payment.setCardNumber(cardNumber);
        payment.setStatus("SUCCESS");
        paymentRepo.save(payment);
        basket.getOrders().clear();
        basketRepo.save(basket);
        return "Payment of $" + String.format("%.2f", payment.getTotals()) + " was successful. Your order has been placed!";
    }

    public String getProductDetails(String id) {
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + id));
        String fileUrl = (product.getFile() != null && product.getFile().getUrl() != null)
                ? product.getFile().getUrl()
                : "No image available";
        boolean isFavorite = favoriteRepo.findByProduct(product)
                .map(Favorite::isFavorite)
                .orElse(false);
        String favoriteMark = isFavorite ? "❤️" : "";
        String productDetails = String.format(
                "%s \nProduct Name: %s \nWeight: %d ml\nPrice: %d $\nDescription: %s \nPicture: %s",
                favoriteMark,product.getName(), product.getPrice(), product.getDescription(), fileUrl);
        return productDetails;
    }

    public List<Product> getProduct() {
        List<Product> all = productRepo.findAll();
        return all;
    }

    public List<Product> searchProduct(String query) {
        return productRepo.findByNameContainingIgnoreCase(query);
    }

    public String createSause(SauseDTO sauseDTO, MultipartFile file){
        Restaurant restaurantById = restaurantRepo.findRestaurantById(sauseDTO.restaurantId());
        Files files = s3StorageService.save(file, AWS_PUBLIC);
        files.setUrl(AWS_URL + files.getPath());
        Files savedFile = filesRepo.save(files);
        Sause build = Sause.builder().name(sauseDTO.name())
                .price(sauseDTO.price())
                .restaurant(restaurantById)
                .file(savedFile).build();
        sauseRepo.save(build);
        return "Successfully added sause.";
    }

    public List<Sause> getAllRestaurantSause(String restaurantId){
        Restaurant restaurantById = restaurantRepo.findRestaurantById(restaurantId);
        return sauseRepo.findByRestaurant(restaurantById);
    }

    public String deleteSause(String id){
        Sause sause = sauseRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Sause not found"));
        sauseRepo.delete(sause);
        return "Successfully removed sause.";
    }

    public String createPackage(PackageDTO packageDTO, MultipartFile file){
        Restaurant restaurantById = restaurantRepo.findRestaurantById(packageDTO.restaurantId());
        Files files = s3StorageService.save(file, AWS_PUBLIC);
        files.setUrl(AWS_URL + files.getPath());
        Files savedFile = filesRepo.save(files);
        Package build = Package.builder()
                .name(packageDTO.name())
                .price(packageDTO.price())
                .file(savedFile)
                .restaurant(restaurantById).build();
        packageRepo.save(build);
        return "Successfully added package.";
    }

    public List<Package> getAllRestaurantPackage(String restaurantId){
        Restaurant restaurantById = restaurantRepo.findRestaurantById(restaurantId);
        return packageRepo.findByRestaurant(restaurantById);
    }

    public String deletePackage(String id){
        Package packages = packageRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Package not found"));
        packageRepo.delete(packages);
        return "Successfully removed package.";
    }

}
