package com.ricardo.scalable.ecommerce.platform.userService.integrationTests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ricardo.scalable.ecommerce.platform.userService.model.dto.WishlistCreationDto;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.*;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class WishlistControllerTest {

    @Autowired
    private Environment env;

    @Autowired
    private WebTestClient client;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    @Order(1)
    void testGetById() {
        client.get()
                .uri("/wishlist/1")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .consumeWith(res -> {
                    try {
                        JsonNode json = objectMapper.readTree(res.getResponseBody());
                        assertAll(
                            () -> assertNotNull(json),
                            () -> assertEquals(1, json.path("id").asLong()),
                            () -> assertEquals(1, json.path("user").path("id").asLong()),
                            () -> assertEquals(1, json.path("productSku").path("id").asLong()),
                            () -> assertEquals("alejandro", json.path("user").path("firstName").asText()),
                            () -> assertEquals("iPhone 15", json.path("productSku").path("product").path("name").asText())
                        );
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @Test
    @Order(2)
    void testGetByIdNotFound() {
        client.get()
                .uri("/wishlist/100")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(3)
    void testGetByUserId() {
        client.get()
                .uri("/wishlist/user/1")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .consumeWith(res -> {
                    try {
                        JsonNode json = objectMapper.readTree(res.getResponseBody());
                        assertAll(
                            () -> assertNotNull(json),
                            () -> assertEquals(3, json.size()),
                            () -> assertEquals(1, json.get(0).path("id").asLong()),
                            () -> assertEquals(1, json.get(0).path("user").path("id").asLong()),
                            () -> assertEquals(1, json.get(0).path("productSku").path("id").asLong()),
                            () -> assertEquals("alejandro", json.get(0).path("user").path("firstName").asText()),
                            () -> assertEquals("Balon premier league 2025", json.get(2).path("productSku").path("product").path("name").asText())
                        );
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @Test
    @Order(4)
    void testGetByUserIdNotFound() {
        client.get()
                .uri("/wishlist/user/100")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(5)
    void testGetByProductSkuId() {
        client.get()
                .uri("/wishlist/product-sku/1")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .consumeWith(res -> {
                    try {
                        JsonNode json = objectMapper.readTree(res.getResponseBody());
                        assertAll(
                            () -> assertNotNull(json),
                            () -> assertEquals(2, json.size()),
                            () -> assertEquals(1, json.get(0).path("id").asLong()),
                            () -> assertEquals(7, json.get(1).path("id").asLong()),
                            () -> assertEquals(1, json.get(0).path("user").path("id").asLong()),
                            () -> assertEquals(3, json.get(1).path("user").path("id").asLong()),
                            () -> assertEquals(1, json.get(0).path("productSku").path("id").asLong()),
                            () -> assertEquals(1, json.get(1).path("productSku").path("id").asLong()),
                            () -> assertEquals("alejandro", json.get(0).path("user").path("firstName").asText()),
                            () -> assertEquals("pepe", json.get(1).path("user").path("firstName").asText()),
                            () -> assertEquals("iPhone 15", json.get(0).path("productSku").path("product").path("name").asText()),
                            () -> assertEquals("iPhone 15", json.get(1).path("productSku").path("product").path("name").asText())
                        );
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @Test
    @Order(6)
    void testGetByProductSkuIdNotFound() {
        client.get()
                .uri("/wishlist/product-sku/100")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(7)
    void testGetAll() {
        client.get()
                .uri("/wishlist")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .consumeWith(res -> {
                    try {
                        JsonNode json = objectMapper.readTree(res.getResponseBody());
                        assertAll(
                            () -> assertNotNull(json),
                            () -> assertTrue(json.isArray()),
                            () -> assertEquals(9, json.size())
                        );
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @Test
    @Order(8)
    void testCreateWishlist() {
        WishlistCreationDto requestBody = new WishlistCreationDto(2L, 9L);

        client.post()
                .uri("/wishlist")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .consumeWith(res -> {
                    try {
                        JsonNode json = objectMapper.readTree(res.getResponseBody());
                        assertAll(
                            () -> assertNotNull(json),
                            () -> assertEquals(10, json.path("id").asLong()),
                            () -> assertEquals(2, json.path("user").path("id").asLong()),
                            () -> assertEquals(9, json.path("productSku").path("id").asLong()),
                            () -> assertEquals("ester", json.path("user").path("firstName").asText()),
                            () -> assertEquals("Polera Puma", json.path("productSku").path("product").path("name").asText())
                        );
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @Test
    @Order(9)
    void testCreateWishlistBadRequest() {
        WishlistCreationDto requestBody = new WishlistCreationDto();

        client.post()
                .uri("/wishlist")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    @Order(10)
    void testCreateWishlistNotFound() {
        WishlistCreationDto requestBodyWithNotExistingUserId = new WishlistCreationDto(100L, 1L);

        client.post()
                .uri("/wishlist")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBodyWithNotExistingUserId)
                .exchange()
                .expectStatus().isNotFound();

        WishlistCreationDto requestBodyWithNotExistingProductSkuId = new WishlistCreationDto(1L, 100L);

        client.post()
                .uri("/wishlist")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBodyWithNotExistingProductSkuId)
                .exchange()
                .expectStatus().isNotFound();

        WishlistCreationDto requestBodyWithNotExistingIds = new WishlistCreationDto(100L, 100L);

        client.post()
                .uri("/wishlist")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBodyWithNotExistingIds)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(11)
    void testUpdateWishlist() {
        WishlistCreationDto requestBody = new WishlistCreationDto(2L, 10L);

        client.put()
                .uri("/wishlist/10")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .consumeWith(res -> {
                    try {
                        JsonNode json = objectMapper.readTree(res.getResponseBody());
                        assertAll(
                            () -> assertNotNull(json),
                            () -> assertEquals(10, json.path("id").asLong()),
                            () -> assertEquals(2, json.path("user").path("id").asLong()),
                            () -> assertEquals(10, json.path("productSku").path("id").asLong()),
                            () -> assertEquals("ester", json.path("user").path("firstName").asText()),
                            () -> assertEquals("Polera Puma", json.path("productSku").path("product").path("name").asText())
                        );
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @Test
    @Order(12)
    void testUpdateWishlistNotFound() {
        WishlistCreationDto requestBody = new WishlistCreationDto(2L, 10L);

        client.put()
                .uri("/wishlist/100")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .exchange()
                .expectStatus().isNotFound();

        WishlistCreationDto requestBodyWithNotExistingUserId = new WishlistCreationDto(200L, 10L);

        client.put()
                .uri("/wishlist/1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBodyWithNotExistingUserId)
                .exchange()
                .expectStatus().isNotFound();

        WishlistCreationDto requestBodyWithNotExistingProductSkuId = new WishlistCreationDto(2L, 100L);

        client.put()
                .uri("/wishlist/1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBodyWithNotExistingProductSkuId)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(13)
    void testUpdateWishlistBadRequest() {
        WishlistCreationDto requestBody = new WishlistCreationDto();

        client.put()
                .uri("/wishlist/1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    @Order(14)
    void testDeleteWishlist() {
        client.delete()
                .uri("/wishlist/10")
                .exchange()
                .expectStatus().isNoContent();

        client.get()
                .uri("/wishlist")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .consumeWith(res -> {
                    try {
                        JsonNode json = objectMapper.readTree(res.getResponseBody());
                        assertAll(
                            () -> assertNotNull(json),
                            () -> assertTrue(json.isArray()),
                            () -> assertEquals(9, json.size())
                        );
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @Test
    @Order(15)
    void testGetDeletedWishlist() {
        client.get()
                .uri("/wishlist/10")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testProfile() {
        assertArrayEquals(new String[]{"test"}, env.getActiveProfiles());
    }

    @Test
    void testApplicationPropertiesFile() {
        assertEquals("jdbc:h2:mem:public;NON_KEYWORDS=value", env.getProperty("spring.datasource.url"));
    }

}
