package com.ricardo.scalable.ecommerce.platform.userService.integrationTests;

import static com.ricardo.scalable.ecommerce.platform.userService.services.testData.AddressControllerTestData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

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
import com.ricardo.scalable.ecommerce.platform.userService.repositories.dto.AddressCreationDto;

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
    @Order(11)
    void testGetAddressByUserIdAndCity() {
        client.get()
                .uri("/addresses/user/3/city/Punta Arenas")
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
                                () -> assertEquals(6L, json.get(0).path("id").asLong()),
                                () -> assertEquals(3L, json.get(0).path("user").path("id").asLong()),
                                () -> assertEquals("Casa en Punta Arenas", json.get(0).path("title").asText()),
                                () -> assertEquals("Calle Magallanes 1415", json.get(0).path("addressLine1").asText())
                        );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @Test
    @Order(12)
    void testGetAddressByUserIdAndCityNotFound() {
        client.get()
                .uri("/addresses/user/3/city/Barcelona")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(13)
    void testGetAddressByUserIdAndCommune() {
        client.get()
                .uri("/addresses/user/1/commune/Antofagasta")
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
    @Order(14)
    void testGetAddressByUserIdAndCommuneNotFound() {
        client.get()
                .uri("/addresses/user/1/commune/Providencia")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(15)
    void testGetAddressByUserIdAndPostalCode() {
        client.get()
                .uri("/addresses/user/2/postalCode/1100000")
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
                                () -> assertEquals(4L, json.get(0).path("id").asLong()),
                                () -> assertEquals(2L, json.get(0).path("user").path("id").asLong()),
                                () -> assertEquals("Casa en Iquique", json.get(0).path("title").asText()),
                                () -> assertEquals("Calle Thompson 1011", json.get(0).path("addressLine1").asText())
                        );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @Test
    @Order(16)
    void testGetAddressByUserIdAndPostalCodeNotFound() {
        client.get()
                .uri("/addresses/user/2/postalCode/9999999")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(17)
    void testGetAddressByUserIdAndLandmark() {
        client.get()
                .uri("/addresses/user/3/landmark/Cerca de la Plaza de Armas")
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
                                () -> assertEquals(5L, json.get(0).path("id").asLong()),
                                () -> assertEquals(3L, json.get(0).path("user").path("id").asLong()),
                                () -> assertEquals("Casa en Chillán", json.get(0).path("title").asText()),
                                () -> assertEquals("Avenida O’Higgins 1213", json.get(0).path("addressLine1").asText())
                        );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @Test
    @Order(18)
    void testGetAddressByUserIdAndLandmarkNotFound() {
        client.get()
                .uri("/addresses/user/3/landmark/Cerca del Cerro San Cristóbal")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(19)
    void testGetAllAddresses() {
        client.get()
                .uri("/addresses")
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
                                () -> assertEquals(6, json.size()),
                                () -> assertEquals(1L, json.get(0).path("id").asLong()),
                                () -> assertEquals(2L, json.get(1).path("id").asLong()),
                                () -> assertEquals(3L, json.get(2).path("id").asLong()),
                                () -> assertEquals(4L, json.get(3).path("id").asLong()),
                                () -> assertEquals(5L, json.get(4).path("id").asLong()),
                                () -> assertEquals(6L, json.get(5).path("id").asLong())
                        );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @Test
    @Order(20)
    void testCreateAddress() {
        AddressCreationDto requestBody = createAddressCreationDto();

        client.post()
                .uri("/addresses")
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
                                () -> assertEquals(7L, json.path("id").asLong()),
                                () -> assertEquals(1L, json.path("user").path("id").asLong()),
                                () -> assertEquals("Casa en Viña del Mar", json.path("title").asText()),
                                () -> assertEquals("Avenida San Martín 456", json.path("addressLine1").asText()),
                                () -> assertEquals("Departamento 3C", json.path("addressLine2").asText()),
                                () -> assertEquals("Chile", json.path("country").asText()),
                                () -> assertEquals("Viña del Mar", json.path("city").asText()),
                                () -> assertEquals("Viña del Mar", json.path("commune").asText()),
                                () -> assertEquals("2520000", json.path("postalCode").asText()),
                                () -> assertEquals("Cerca del Reloj de Flores", json.path("landmark").asText())
                        );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @Test
    @Order(21)
    void testCreateAddressBadRequest() {
        AddressCreationDto requestBody = new AddressCreationDto();

        client.post()
                .uri("/addresses")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    @Order(22)
    void testCreateAddressNotfound() {
        AddressCreationDto requestBody = createAddressCreationDto();
        requestBody.setUserId(100L);

        client.post()
                .uri("/addresses")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
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
