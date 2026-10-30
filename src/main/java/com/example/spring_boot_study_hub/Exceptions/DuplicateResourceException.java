package com.example.spring_boot_study_hub.Exceptions;

public class DuplicateResourceException extends RuntimeException {
    public DuplicateResourceException(String message) {
        super(message);
    }

    public static DuplicateResourceException forProduct(String name) {
        return new DuplicateResourceException("Product already exists with name: " + name);
    }
}
