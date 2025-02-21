package com.ricardo.scalable.ecommerce.platform.userService.services.testData.productSku;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

import com.ricardo.scalable.ecommerce.platform.libs_common.entities.Brand;

public class BrandTestData {

    public static Optional<Brand> createBrand001() {
        Brand brand = new Brand(
            1L, 
            "Apple", 
            "Marca Apple", 
            "logo2.png", 
            Timestamp.from(Instant.now()), 
            Timestamp.from(Instant.now())
        );

        return Optional.of(brand);
    }

    public static Optional<Brand> createBrand002() {
        Brand brand = new Brand(
            2L, 
            "Samsung", 
            "Marca Samsung", 
            "logo2.png", 
            Timestamp.from(Instant.now()), 
            Timestamp.from(Instant.now())
        );

        return Optional.of(brand);
    }

    public static Optional<Brand> createBrand003() {
        Brand brand = new Brand(
            3L, 
            "Americanino", 
            "Marca Americanino", 
            "logo5.png", 
            Timestamp.from(Instant.now()), 
            Timestamp.from(Instant.now())
        );

        return Optional.of(brand);
    }

    public static Optional<Brand> createUnknownBrand() {
        Brand brand = new Brand(
            4L, 
            "Unknown", 
            "Unknown", 
            "Unknown", 
            Timestamp.from(Instant.now()), 
            Timestamp.from(Instant.now())
        );

        return Optional.of(brand);
    }

}
