package com.ricardo.scalable.ecommerce.platform.userService.integrationTests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

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
    void testProfile() {
        assertArrayEquals(new String[]{"test"}, env.getActiveProfiles());
    }

    @Test
    void testApplicationPropertiesFile() {
        assertEquals("jdbc:h2:mem:public;NON_KEYWORDS=value", env.getProperty("spring.datasource.url"));
    }

}
