package com.ricardo.scalable.ecommerce.platform.userService.services.testData.productSku;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

import com.ricardo.scalable.ecommerce.platform.libs_common.entities.Category;

public class CategoryTestData {

    public static Optional<Category> createParentCategory001() {
        Category category = new Category(
            1L, 
            "Tecnología", 
            "Descripcion tecnología",
            null,
            Timestamp.from(Instant.now()), 
            Timestamp.from(Instant.now())
        );

        return Optional.of(category);
    }

    public static Optional<Category> createParentCategory002() {
        Category category = new Category(
            2L, 
            "Deportes", 
            "Descripcion deportes",
            null, 
            Timestamp.from(Instant.now()), 
            Timestamp.from(Instant.now())
        );

        return Optional.of(category);
    }

    public static Optional<Category> createParentCategory003() {
        Category category = new Category(
            3L, 
            "Ropa Hombre", 
            "Descripcion ropa hombre",
            null, 
            Timestamp.from(Instant.now()), 
            Timestamp.from(Instant.now())
        );

        return Optional.of(category);
    }

    public static Optional<Category> createSubCategory001() {
        Category category = new Category(
            4L, 
            "Computadoras", 
            "Descripcion computadoras",
            createParentCategory001().orElseThrow(), 
            Timestamp.from(Instant.now()), 
            Timestamp.from(Instant.now())
        );

        return Optional.of(category);
    }

    public static Optional<Category> createSubCategory002() {
        Category category = new Category(
            5L, 
            "Celulares", 
            "Descripcion celulares",
            createParentCategory001().orElseThrow(), 
            Timestamp.from(Instant.now()), 
            Timestamp.from(Instant.now())
        );

        return Optional.of(category);
    }

    public static Optional<Category> createSubCategory003() {
        Category category = new Category(
            6L, 
            "Poleras", 
            "Descripcion poleras",
            createParentCategory003().orElseThrow(), 
            Timestamp.from(Instant.now()), 
            Timestamp.from(Instant.now())
        );

        return Optional.of(category);
    }

    public static Optional<Category> createSubCategory004() {
        Category category = new Category(
            7L, 
            "Pantalones", 
            "Descripcion pantalones",
            createParentCategory003().orElseThrow(), 
            Timestamp.from(Instant.now()), 
            Timestamp.from(Instant.now())
        );

        return Optional.of(category);
    }

    public static Optional<Category> createUnknownCategory() {
        Category category = new Category(
            8L, 
            "Unknown", 
            "Unknown",
            null, 
            Timestamp.from(Instant.now()), 
            Timestamp.from(Instant.now())
        );

        return Optional.of(category);
    }

}
