package com.ricardo.scalable.ecommerce.platform.userService.services.testData.productSku;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

import com.ricardo.scalable.ecommerce.platform.libs_common.entities.ProductAttribute;

public class ProductAttributeTestData {

    public static Optional<ProductAttribute> createProductAttribute001() {
        return Optional.of(
            new ProductAttribute(
            1L, "color", "rojo", Timestamp.from(Instant.now()), Timestamp.from(Instant.now()))
        );
    }

    public static Optional<ProductAttribute> createProductAttribute002() {
        return Optional.of(
            new ProductAttribute(
            2L, "color", "azul", Timestamp.from(Instant.now()), Timestamp.from(Instant.now()))
        );
    }

    public static Optional<ProductAttribute> createProductAttribute003() {
        return Optional.of(
            new ProductAttribute(
            3L, "color", "negro", Timestamp.from(Instant.now()), Timestamp.from(Instant.now()))
        );
    }

    public static Optional<ProductAttribute> createProductAttribute004() {
        return Optional.of(
            new ProductAttribute(
            4L, "size", "S", Timestamp.from(Instant.now()), Timestamp.from(Instant.now()))
        );
    }

    public static Optional<ProductAttribute> createProductAttribute005() {
        return Optional.of(
            new ProductAttribute(
            5L, "size", "M", Timestamp.from(Instant.now()), Timestamp.from(Instant.now()))
        );
    }

    public static Optional<ProductAttribute> createProductAttribute006() {
        return Optional.of(
            new ProductAttribute(
            6L, "size", "L", Timestamp.from(Instant.now()), Timestamp.from(Instant.now()))
        );
    }

    public static Optional<ProductAttribute> createProductAttribute007() {
        return Optional.of(
            new ProductAttribute(
            7L, "size", "none-size", Timestamp.from(Instant.now()), Timestamp.from(Instant.now()))
        );
    }

    public static Optional<ProductAttribute> createProductAttribute008() {
        return Optional.of(
            new ProductAttribute(
            8L, "color", "none-color", Timestamp.from(Instant.now()), Timestamp.from(Instant.now()))
        );
    }

}
