package com.ricardo.scalable.ecommerce.platform.userService.integrationTests;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import org.checkerframework.checker.units.qual.t;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class AddressControllerTest {

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
    void testGetAddressById() {
        client.get()
                .uri("/addresses/1")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .consumeWith(res -> {
                    try {
                        JsonNode json = objectMapper.readTree(res.getResponseBody());
                        assertAll(
                                () -> assertNotNull(json),
                                () -> assertEquals(1L, json.path("id").asLong()),
                                () -> assertEquals(1L, json.path("user").path("id").asLong()),
                                () -> assertEquals("Casa en Antofagasta", json.path("title").asText()),
                                () -> assertEquals("Avenida Argentina 123", json.path("addressLine1").asText()),
                                () -> assertEquals("Departamento 5A", json.path("addressLine2").asText()),
                                () -> assertEquals("Chile", json.path("country").asText()),
                                () -> assertEquals("Antofagasta", json.path("city").asText()),
                                () -> assertEquals("Antofagasta", json.path("commune").asText()),
                                () -> assertEquals("1240000", json.path("postalCode").asText()),
                                () -> assertEquals("Cerca del Mall Plaza Antofagasta", json.path("landmark").asText())  
                        );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @Test
    @Order(2)
    void testGetAddressByIdNotFound() {
        client.get()
                .uri("/addresses/100")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(3)
    void testGetAddressByUserId() {
        client.get()
                .uri("/addresses/user/1")
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
                                () -> assertEquals(2, json.size()),
                                () -> assertEquals(1L, json.get(0).path("id").asLong()),
                                () -> assertEquals(1L, json.get(1).path("user").path("id").asLong()),
                                () -> assertEquals("Casa en Antofagasta", json.get(0).path("title").asText()),
                                () -> assertEquals("Avenida Argentina 123", json.get(0).path("addressLine1").asText()),
                                () -> assertEquals("Oficina en Rancagua", json.get(1).path("title").asText()),
                                () -> assertEquals("Calle Estado 456", json.get(1).path("addressLine1").asText())
                        );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @Test
    @Order(4)
    void testGetAddressByUserIdNotFound() {
        client.get()
                .uri("/addresses/user/100")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(5)
    void testGetAddressByUserIdAndTitle() {
        client.get()
                .uri("/addresses/user/1/title/Casa en Antofagasta")
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
                                () -> assertEquals(1, json.size()),
                                () -> assertEquals(1L, json.get(0).path("id").asLong()),
                                () -> assertEquals(1L, json.get(0).path("user").path("id").asLong()),
                                () -> assertEquals("Casa en Antofagasta", json.get(0).path("title").asText()),
                                () -> assertEquals("Avenida Argentina 123", json.get(0).path("addressLine1").asText())
                        );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @Test
    @Order(6)
    void testGetAddressByUserIdAndTitleNotFound() {
        client.get()
                .uri("/addresses/user/1/title/Casa en Rio de janeiro")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(7)
    void testGetAddressByUserIdAndAddressLine1() {
        client.get()
                .uri("/addresses/user/2/addressLine1/Avenida Diego Portales 789")
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
                                () -> assertEquals(1, json.size()),
                                () -> assertEquals(3L, json.get(0).path("id").asLong()),
                                () -> assertEquals(2L, json.get(0).path("user").path("id").asLong()),
                                () -> assertEquals("Departamento en Puerto Montt", json.get(0).path("title").asText()),
                                () -> assertEquals("Avenida Diego Portales 789", json.get(0).path("addressLine1").asText())
                        );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @Test
    @Order(8)
    void testGetAddressByUserIdAndAddressLine1NotFound() {
        client.get()
                .uri("/addresses/user/2/addressLine1/not exists")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(9)
    void testGetAddressByUserIdAndCountry() {
        client.get()
                .uri("/addresses/user/1/country/Chile")
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
                                () -> assertEquals(2, json.size()),
                                () -> assertEquals(1L, json.get(0).path("id").asLong()),
                                () -> assertEquals(2L, json.get(1).path("id").asLong()),
                                () -> assertEquals(1L, json.get(1).path("user").path("id").asLong()),
                                () -> assertEquals("Casa en Antofagasta", json.get(0).path("title").asText()),
                                () -> assertEquals("Avenida Argentina 123", json.get(0).path("addressLine1").asText()),
                                () -> assertEquals("Oficina en Rancagua", json.get(1).path("title").asText()),
                                () -> assertEquals("Calle Estado 456", json.get(1).path("addressLine1").asText())
                        );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @Test
    @Order(10)
    void testGetAddressByUserIdAndCountryNotFound() {
        client.get()
                .uri("/addresses/user/1/country/Argentina")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testProfile() {
        String[] activeProfiles = env.getActiveProfiles();
        assertArrayEquals(new String[] { "test" }, activeProfiles);
    }

    @Test
    void testApplicationPropertyFile() {
        assertEquals("jdbc:h2:mem:public", env.getProperty("spring.datasource.url"));
    }

}
