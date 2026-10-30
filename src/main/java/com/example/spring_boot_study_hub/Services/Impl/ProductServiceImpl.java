package com.example.spring_boot_study_hub.Services.Impl;

import com.example.spring_boot_study_hub.Dtos.Request.ProductRequestDto;
import com.example.spring_boot_study_hub.Dtos.Response.ApiResponse;
import com.example.spring_boot_study_hub.Dtos.Response.ProductResponse;
import com.example.spring_boot_study_hub.Entities.Product;
import com.example.spring_boot_study_hub.Exceptions.DuplicateResourceException;
import com.example.spring_boot_study_hub.Exceptions.ResourceNotFoundException;
import com.example.spring_boot_study_hub.Repositories.ProductRepo;
import com.example.spring_boot_study_hub.Services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepository;

    // Create product
    @Override
    @Transactional
    public ProductResponse createProduct(ProductRequestDto request) {
        simulateDelay();
        log.info("Creating product with name: {}", request.getName());

        if (productRepository.existsByNameIgnoreCase(request.getName())) {
            throw new DuplicateResourceException(
                    "Product already exists with name: " + request.getName());
        }

        Product product = new Product();
        mapRequestToEntity(request, product);

        return mapToResponse(productRepository.save(product));
    }

    // Get product by id
    @Override
    @Cacheable(value = "products", key = "#id", unless = "#result == null")
    public ProductResponse getProductById(Long id) {
        simulateDelay();
        log.info("Fetching product with id: {}", id);
        Optional<Product> optionalProduct = productRepository.findById(id);

        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            return mapToResponse(product);

        } else {
            log.warn("Product not found with id: {}", id);
            throw new ResourceNotFoundException("Product not found with id: " + id);
        }
    }

    // Get all products
    @Override
    public List<ProductResponse> getAllProducts(String search) {
        simulateDelay();
        log.info("Fetching products, search term: {}", search);

        List<Product> products = (search == null || search.isBlank())
                ? productRepository.findAll()
                : productRepository.findByNameContainingIgnoreCase(search.trim());

        return products.stream()
                .map(this::mapToResponse)
                .toList();
    }

    // Update product
    @Override
    @Transactional
    @CachePut(value = "products", key = "#id", unless = "#result == null")
    public ProductResponse updateProduct(Long id, ProductRequestDto request) {
        simulateDelay();
        log.info("Updating product with id: {}", id);
        Optional<Product> optionalProduct = productRepository.findById(id);

        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();

            if (!product.getName().equalsIgnoreCase(request.getName())
                    && productRepository.existsByNameIgnoreCase(request.getName())) {
                throw new DuplicateResourceException(request.getName());
            }

            mapRequestToEntity(request, product);
            return mapToResponse(productRepository.save(product));

        } else {
            log.warn("Product not found with id: {}", id);
            throw new ResourceNotFoundException("Product not found with id: " + id);
        }
    }

    // Delete product
    @Override
    @Transactional
    @CacheEvict(value = "products", key = "#id")
    public void deleteProduct(Long id) {
        simulateDelay();
        log.info("Deleting product with id: {}", id);
        Optional<Product> optionalProduct = productRepository.findById(id);

        Product product;
        if (optionalProduct.isPresent()) {
            product = optionalProduct.get();
        } else {
            log.warn("Product not found with id: {}", id);
            throw new ResourceNotFoundException("Product not found with id: " + id);
        }
        productRepository.delete(product);
    }

    // Mappers
    private void mapRequestToEntity(ProductRequestDto request, Product product) {
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());
    }

    private ProductResponse mapToResponse(Product product) {
        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());
        response.setQuantity(product.getQuantity());
        response.setCreatedAt(product.getCreatedAt());
        response.setUpdatedAt(product.getUpdatedAt());
        return response;
    }

    private void simulateDelay() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.warn("Delay was interrupted", e);
        }
    }
}