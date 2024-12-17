package uz.pdp.fast_food_app.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.fast_food_app.dto.*;
import uz.pdp.fast_food_app.model.Address;
import uz.pdp.fast_food_app.model.Package;
import uz.pdp.fast_food_app.model.Product;
import uz.pdp.fast_food_app.model.Sause;
import uz.pdp.fast_food_app.repository.SauseRepo;
import uz.pdp.fast_food_app.service.DeliveryService;
import uz.pdp.fast_food_app.service.FavoriteService;
import uz.pdp.fast_food_app.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/user/products")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Fast Food App", description = "Manage products including adding, updating, removing, and ordering.")
public class ProductController {
    private final ProductService productService;
    private final DeliveryService deliveryService;
    private final FavoriteService favoriteService;

    public ProductController(ProductService productService, DeliveryService deliveryService, FavoriteService favoriteService) {
        this.productService = productService;
        this.deliveryService = deliveryService;
        this.favoriteService = favoriteService;
    }

    @PreAuthorize("hasAuthority('ROLE_CREATE')")
    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> addMedicine(@RequestParam("name") String name,
                                              @RequestParam("price") Long price,
                                              @RequestParam("description") String description,
                                              @RequestParam(value = "files") MultipartFile files,
                                              @RequestParam("restaurantId") String restaurantId) {
        try {
            ProductDTO productDTO = new ProductDTO(name, description, price, restaurantId);
            String result = productService.addProduct(productDTO, files);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to add product: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('ROLE_UPDATE')")
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateMedicine(@PathVariable("id") String id,
                                                 @RequestParam("name") String name,
                                                 @RequestParam("price") Long price,
                                                 @RequestParam("description") String description,
                                                 @RequestParam("restaurantId") String restaurantId) {
        try {
            ProductDTO productDTO = new ProductDTO(name, description, price, restaurantId);
            String result = productService.updateProduct(id, productDTO);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update product: " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasAuthority('ROLE_DELETE')")
    @DeleteMapping("/remove/{id}")
    public ResponseEntity<String> removeMedicine(@PathVariable("id") String id) {
        try {
            String result = productService.removeProduct(id);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to remove product: " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasAuthority('ROLE_ORDER')")
    @PostMapping("/order/{id}")
    public ResponseEntity<String> orderProduct(@PathVariable("id") String id,
                                                @RequestParam("count") int quantity,
                                                @RequestParam("packages") String packages,
                                                @RequestParam(required = false, value = "sause") String sause,
                                                @RequestParam("restaurantId") String restaurantId) {
        try {
            OrderDTO orderDTO = new OrderDTO(id, quantity, packages, sause, restaurantId);
            String result = productService.orderProduct(orderDTO);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to order product: " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasPermission('ROLE_SHOW_CARD')")
    @GetMapping("/myCard")
    public ResponseEntity<String> getMyCard() {
        String s = productService.myCard();
        return new ResponseEntity<>(s, HttpStatus.OK);
    }

    @PreAuthorize("hasPermission('ROLE_BUY')")
    @PostMapping("/cardNumber")
    public ResponseEntity<String> addCardNumber(@RequestParam("cardNumber") String cardNumber) {
        String buy = productService.buy(cardNumber);
        return new ResponseEntity<>(buy, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_GET')")
    @GetMapping("/get/{id}")
    public ResponseEntity<String> getProduct(@PathVariable("id") String id) {
        try {
            String result = productService.getProductDetails(id);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to retrieve product: " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasAuthority('ROLE_GET_ALL')")
    @GetMapping("/getAll")
    public ResponseEntity<List<Product>> getAllProduct() {
        List<Product> product = productService.getProduct();
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_LOCATION')")
    @PostMapping("/location")
    public ResponseEntity<Address> create(AddressDTO addressDTO) {
        Address address = deliveryService.create(addressDTO);
        return new ResponseEntity<>(address, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ROLE_GET_USER_LOCATION')")
    @GetMapping("/getUserLocation")
    public ResponseEntity<Address> getUserLocation() {
        Address userAddress = deliveryService.getUserAddress();
        return new ResponseEntity<>(userAddress, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_UPDATE_LOCATION')")
    @PutMapping("/updateLocation/{id}")
    public ResponseEntity<Address> updateLocation(@PathVariable("id") String id, AddressDTO addressDTO) {
        Address address = deliveryService.update(id, addressDTO);
        return new ResponseEntity<>(address, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_FAVORITE')")
    @PostMapping("/mark")
    public ResponseEntity<String> markAsFavorite(@RequestParam String productId) {
        String result = favoriteService.markAsFavorite(productId);
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasAuthority('ROLE_SEARCH')")
    @GetMapping("/search")
    public List<Product> searchFoodItems(@RequestParam String query) {
        return productService.searchProduct(query);
    }

    @PreAuthorize("hasAuthority('ROLE_SAUSE_CREATE')")
    @PostMapping("/create_sause")
    public ResponseEntity<String> create_sause(@RequestParam("name") String name,
                                         @RequestParam("price") long price,
                                         @RequestParam("file") MultipartFile file,
                                         @RequestParam("restaurantId") String restaurantId){
        try {
            SauseDTO sauseDTO = new SauseDTO(name, price, restaurantId);
            String result = productService.createSause(sauseDTO, file);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to add sause: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('ROLE_SAUSE_GET')")
    @GetMapping("/getAllSauseRestaurand/{restaurantId}")
    public ResponseEntity<List<Sause>> getAllSause(@PathVariable("restaurantId") String restaurantId){
        List<Sause> allRestaurantSause = productService.getAllRestaurantSause(restaurantId);
        return ResponseEntity.ok(allRestaurantSause);
    }

    @PreAuthorize("hasAuthority('ROLE_SAUSE_DELETE')")
    @DeleteMapping("/delete_sause/{id}")
    public ResponseEntity<String> delete_sause(@PathVariable("id") String id){
        try {
            String result = productService.deleteSause(id);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete sause: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('ROLE_PACKAGE_CREATE')")
    @PostMapping("/createPackage")
    public ResponseEntity<String> create_package(@RequestParam("name") String name,
                                         @RequestParam("price") long price,
                                         @RequestParam("file") MultipartFile file,
                                         @RequestParam("restaurantId") String restaurantId){
        try {
            PackageDTO packageDTO = new PackageDTO(name, price, restaurantId);
            String result = productService.createPackage(packageDTO, file);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to add Package: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('ROLE_PACKAGE_GET')")
    @GetMapping("/getAllPackageRestaurand/{restaurantId}")
    public ResponseEntity<List<Package>> getAllPackage(@PathVariable("restaurantId") String restaurantId){
        List<Package> allRestaurantPackage = productService.getAllRestaurantPackage(restaurantId);
        return ResponseEntity.ok(allRestaurantPackage);
    }

    @PreAuthorize("hasAuthority('ROLEPACKAGEE_DELETE')")
    @DeleteMapping("/deletePackage/{id}")
    public ResponseEntity<String> delete_package(@PathVariable("id") String id){
        try {
            String result = productService.deletePackage(id);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete Package: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
