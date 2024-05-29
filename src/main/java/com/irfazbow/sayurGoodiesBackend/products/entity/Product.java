package com.irfazbow.sayurGoodiesBackend.products.entity;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Product {
    private String id;
    @NotNull
    @Min(value = 0, message = "Price must be non-negative")
    private double price;
    @NotNull
    @Min(value = 0, message = "Weight must be non-negative")
    private int weight;
    @NotNull
    @NotBlank(message = "Product name is required")
    private String name;
    @NotBlank(message = "Product category is required")
    private String category;
    @NotBlank(message = "Image URL is required")
    private String imageUrl;
    @NotNull(message = "Metadata is required")
    private Metadata metadata;

    public static class Metadata {
        @NotBlank(message = "Unit is required")
        private String unit;

        @Min(value = 0, message = "Weight must be non-negative")
        private int weight;

        @Min(value = 0, message = "Calorie must be non-negative")
        private int calorie;

        @Min(value = 0, message = "Proteins must be non-negative")
        private int proteins;

        @Min(value = 0, message = "Fats must be non-negative")
        private int fats;

        @Min(value = 0, message = "Increment must be non-negative")
        private int increment;

        @Min(value = 0, message = "Carbs must be non-negative")
        private int carbs;

    }
}
