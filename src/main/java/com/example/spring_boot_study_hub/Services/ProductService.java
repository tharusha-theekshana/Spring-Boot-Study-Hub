package com.example.spring_boot_study_hub.Services;

import com.example.spring_boot_study_hub.Dtos.Request.ProductRequestDto;
import com.example.spring_boot_study_hub.Dtos.Response.ApiResponse;
import com.example.spring_boot_study_hub.Dtos.Response.ProductResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {

    ProductResponse createProduct(ProductRequestDto request);

    ProductResponse getProductById(Long id);

    List<ProductResponse> getAllProducts(String search);

    ProductResponse updateProduct(Long id, ProductRequestDto request);

    void deleteProduct(Long id);
}