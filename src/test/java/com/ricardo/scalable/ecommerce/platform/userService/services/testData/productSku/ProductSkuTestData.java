package com.ricardo.scalable.ecommerce.platform.userService.services.testData.productSku;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

import com.ricardo.scalable.ecommerce.platform.libs_common.entities.Product;
import com.ricardo.scalable.ecommerce.platform.libs_common.entities.ProductAttribute;
import com.ricardo.scalable.ecommerce.platform.libs_common.entities.ProductSku;

import static com.ricardo.scalable.ecommerce.platform.userService.services.testData.productSku.ProductAttributeTestData.*;
import static com.ricardo.scalable.ecommerce.platform.userService.services.testData.productSku.ProductTestData.*;

public class ProductSkuTestData {

    public static Optional<ProductSku> createProductSku001() {
        ProductSku productSku = new ProductSku();
        Product product = createProduct001().orElseThrow();
        ProductAttribute sizeAttribute = createProductAttribute007().orElseThrow();
        ProductAttribute colorAttribute = createProductAttribute003().orElseThrow();

        productSku.setId(1L);
        productSku.setProduct(product);
        productSku.setSizeAttribute(sizeAttribute);
        productSku.setColorAttribute(colorAttribute);
        productSku.setSku("SKU001");
        productSku.setPrice(1000.00);
        productSku.setStock(20);
        productSku.setIsActive(true);
        productSku.setIsFeatured(false);
        productSku.setIsOnSale(false);
        productSku.setCreatedAt(Timestamp.from(Instant.now()));
        productSku.setUpdatedAt(Timestamp.from(Instant.now()));

        return Optional.of(productSku);
    }

    public static Optional<ProductSku> createProductSku002() {
        ProductSku productSku = new ProductSku();
        Product product = createProduct002().orElseThrow();
        ProductAttribute sizeAttribute = createProductAttribute007().orElseThrow();
        ProductAttribute colorAttribute = createProductAttribute002().orElseThrow();

        productSku.setId(2L);
        productSku.setProduct(product);
        productSku.setSizeAttribute(sizeAttribute);
        productSku.setColorAttribute(colorAttribute);
        productSku.setSku("SKU002");
        productSku.setPrice(899.99);
        productSku.setStock(20);
        productSku.setIsActive(true);
        productSku.setIsFeatured(false);
        productSku.setIsOnSale(false);
        productSku.setCreatedAt(Timestamp.from(Instant.now()));
        productSku.setUpdatedAt(Timestamp.from(Instant.now()));

        return Optional.of(productSku);
    }

    public static Optional<ProductSku> createProductSku003() {
        ProductSku productSku = new ProductSku();
        Product product = createProduct003().orElseThrow();
        ProductAttribute sizeAttribute = createProductAttribute007().orElseThrow();
        ProductAttribute colorAttribute = createProductAttribute001().orElseThrow();

        productSku.setId(3L);
        productSku.setProduct(product);
        productSku.setSizeAttribute(sizeAttribute);
        productSku.setColorAttribute(colorAttribute);
        productSku.setSku("SKU003");
        productSku.setPrice(1299.99);
        productSku.setStock(20);
        productSku.setIsActive(true);
        productSku.setIsFeatured(false);
        productSku.setIsOnSale(false);
        productSku.setCreatedAt(Timestamp.from(Instant.now()));
        productSku.setUpdatedAt(Timestamp.from(Instant.now()));

        return Optional.of(productSku);
    }

    public static Optional<ProductSku> createProductSku004() {
        ProductSku productSku = new ProductSku();
        Product product = createProduct004().orElseThrow();
        ProductAttribute sizeAttribute = createProductAttribute004().orElseThrow();
        ProductAttribute colorAttribute = createProductAttribute003().orElseThrow();

        productSku.setId(4L);
        productSku.setProduct(product);
        productSku.setSizeAttribute(sizeAttribute);
        productSku.setColorAttribute(colorAttribute);
        productSku.setSku("SKU004");
        productSku.setPrice(35.00);
        productSku.setStock(10);
        productSku.setIsActive(true);
        productSku.setIsFeatured(false);
        productSku.setIsOnSale(false);
        productSku.setCreatedAt(Timestamp.from(Instant.now()));
        productSku.setUpdatedAt(Timestamp.from(Instant.now()));

        return Optional.of(productSku);
    }

    public static Optional<ProductSku> createProductSku005() {
        ProductSku productSku = new ProductSku();
        Product product = createProduct005().orElseThrow();
        ProductAttribute sizeAttribute = createProductAttribute005().orElseThrow();
        ProductAttribute colorAttribute = createProductAttribute002().orElseThrow();

        productSku.setId(5L);
        productSku.setProduct(product);
        productSku.setSizeAttribute(sizeAttribute);
        productSku.setColorAttribute(colorAttribute);
        productSku.setSku("SKU005");
        productSku.setPrice(25.00);
        productSku.setStock(20);
        productSku.setIsActive(true);
        productSku.setIsFeatured(false);
        productSku.setIsOnSale(false);
        productSku.setCreatedAt(Timestamp.from(Instant.now()));
        productSku.setUpdatedAt(Timestamp.from(Instant.now()));

        return Optional.of(productSku);
    }

    public static Optional<ProductSku> createProductSku006() {
        ProductSku productSku = new ProductSku();
        Product product = createProduct004().orElseThrow();
        ProductAttribute sizeAttribute = createProductAttribute004().orElseThrow();
        ProductAttribute colorAttribute = createProductAttribute001().orElseThrow();

        productSku.setId(6L);
        productSku.setProduct(product);
        productSku.setSizeAttribute(sizeAttribute);
        productSku.setColorAttribute(colorAttribute);
        productSku.setSku("SKU006");
        productSku.setPrice(50.00);
        productSku.setStock(50);
        productSku.setIsActive(true);
        productSku.setIsFeatured(false);
        productSku.setIsOnSale(false);
        productSku.setCreatedAt(Timestamp.from(Instant.now()));
        productSku.setUpdatedAt(Timestamp.from(Instant.now()));

        return Optional.of(productSku);
    }

    public static Optional<ProductSku> createProductSku007() {
        ProductSku productSku = new ProductSku();
        Product product = createProduct005().orElseThrow();
        ProductAttribute sizeAttribute = createProductAttribute006().orElseThrow();
        ProductAttribute colorAttribute = createProductAttribute002().orElseThrow();

        productSku.setId(7L);
        productSku.setProduct(product);
        productSku.setSizeAttribute(sizeAttribute);
        productSku.setColorAttribute(colorAttribute);
        productSku.setSku("SKU007");
        productSku.setPrice(40.00);
        productSku.setStock(20);
        productSku.setIsActive(true);
        productSku.setIsFeatured(false);
        productSku.setIsOnSale(false);
        productSku.setCreatedAt(Timestamp.from(Instant.now()));
        productSku.setUpdatedAt(Timestamp.from(Instant.now()));

        return Optional.of(productSku);
    }

}
