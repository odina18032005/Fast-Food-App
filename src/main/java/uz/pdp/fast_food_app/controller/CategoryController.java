package uz.pdp.fast_food_app.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.fast_food_app.dto.CategoryDTO;
import uz.pdp.fast_food_app.model.Category;
import uz.pdp.fast_food_app.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/category")
@Tag(name = "Category", description = "Categories add, delete and edit here")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PreAuthorize("hasAuthority('ROLE_CATEGORY_CREATE')")
    @PostMapping(value = "/create_category", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Category> create(CategoryDTO categoryDTO, @RequestParam("files")MultipartFile file){
        Category category = categoryService.create(categoryDTO, file);
        return ResponseEntity.ok(category);
    }

    @GetMapping("/getByName")
    public ResponseEntity<Category> getByName(@RequestParam("name") String name){
        Category byName = categoryService.getByName(name);
        return ResponseEntity.ok(byName);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Category> get(@PathVariable("id") String id){
        Category category = categoryService.get(id);
        return ResponseEntity.ok(category);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Category>> getAll(){
        List<Category> categories = categoryService.allCategory();
        return ResponseEntity.ok(categories);
    }

    @PreAuthorize("hasAuthority('ROLE_CATEGORY_UPDATE')")
    @PutMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Category> update(@PathVariable("id") String id, CategoryDTO categoryDTO, @RequestParam("files") MultipartFile files){
        Category update = categoryService.update(id, categoryDTO, files);
        return ResponseEntity.ok(update);
    }

    @PreAuthorize("hasAuthority('ROLE_CATEGORY_DELETE')")
    @DeleteMapping("/remove/{id}")
    public ResponseEntity<String> remove(@PathVariable("id") String id){
        categoryService.remove(id);
        return ResponseEntity.ok("Deleted appointment");
    }
}
