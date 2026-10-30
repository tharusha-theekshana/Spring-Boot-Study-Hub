package com.example.spring_boot_study_hub.Dtos.Request;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class ProductRequestDto {

    @NotBlank(message = "Product name is required")
    @Size(max = 150, message = "Name must not exceed 150 characters")
    private String name;

    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String description;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private BigDecimal price;

    @NotNull(message = "Quantity is required")
    @Min(value = 0, message = "Quantity cannot be negative")
    private Integer quantity;

    public ProductRequestDto() {
    }

    public ProductRequestDto(String name, String description, @NotNull(message = "Price is required") BigDecimal price, @NotNull(message = "Quantity is required") Integer quantity) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
}