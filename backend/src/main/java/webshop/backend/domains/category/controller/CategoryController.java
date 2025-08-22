package webshop.backend.domains.category.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webshop.backend.domains.category.dto.CategoryCreateDto;
import webshop.backend.domains.category.dto.CategoryResponseDto;
import webshop.backend.domains.category.service.CategoryService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/categories")
@Tag(name = "Categories", description = "Endpoints for managing categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(summary = "Get all categories")
    @ApiResponse(responseCode = "200", description = "List of categories returned")
    @GetMapping
    public ResponseEntity<List<CategoryResponseDto>> getAllCategories() {
        log.debug("GET /api/categories called");
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @Operation(summary = "Get category by ID")
    @ApiResponse(responseCode = "200", description = "Category found")
    @ApiResponse(responseCode = "404", description = "Category not found")
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> getCategoryById(@PathVariable Long id) {
        log.debug("GET /api/categories/{} called", id);
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @Operation(summary = "Create a new category")
    @ApiResponse(responseCode = "200", description = "Category created successfully")
    @PostMapping
    public ResponseEntity<CategoryResponseDto> createCategory(@RequestBody CategoryCreateDto categoryDto) {
        log.debug("POST /api/categories called with payload: {}", categoryDto);
        return ResponseEntity.ok(categoryService.createCategory(categoryDto));
    }

    @Operation(summary = "Delete category")
    @ApiResponse(responseCode = "204", description = "Category deleted successfully")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        log.debug("DELETE /api/categories/{} called", id);
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
