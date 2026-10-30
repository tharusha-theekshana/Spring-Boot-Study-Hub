package com.example.spring_boot_study_hub.Controllers;

import com.example.spring_boot_study_hub.Dtos.Request.ProductRequestDto;
import com.example.spring_boot_study_hub.Dtos.Response.ApiResponse;
import com.example.spring_boot_study_hub.Dtos.Response.ProductResponse;
import com.example.spring_boot_study_hub.Services.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Create product
    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponse>> createProduct(@Valid @RequestBody ProductRequestDto request) {
        ProductResponse productResponse = productService.createProduct(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse<>(HttpStatus.CREATED.value(), "Product created successfully ... !", productResponse));
    }

    // Get all products
    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getAllProducts(
            @RequestParam(required = false) String search) {
        List<ProductResponse> products = productService.getAllProducts(search);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>(HttpStatus.OK.value(), "Products fetch successfully ... !", products));
    }

    // Get product By id
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> getProductById(@PathVariable Long id) {
        ProductResponse productResponse = productService.getProductById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>(HttpStatus.OK.value(), "Product fetch successfully ... !", productResponse));
    }

    // Update product
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequestDto request) {
        ProductResponse productResponse = productService.updateProduct(id, request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>(
                        HttpStatus.OK.value(), "Product updated successfully ... !", productResponse));

    }

    // Delete product
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>(HttpStatus.OK.value(), "Product with ID " + id + " deleted successfully!"));
    }
}