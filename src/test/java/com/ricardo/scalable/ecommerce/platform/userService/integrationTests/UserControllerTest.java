package com.ricardo.scalable.ecommerce.platform.userService.integrationTests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import org.springframework.test.web.reactive.server.WebTestClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ricardo.scalable.ecommerce.platform.userService.entities.Role;
import com.ricardo.scalable.ecommerce.platform.userService.entities.User;
import com.ricardo.scalable.ecommerce.platform.userService.repositories.dto.UserRegisterDto;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.*;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.print.attribute.standard.Media;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import org.springframework.core.env.Environment;

@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class UserControllerTest {

    @Autowired
    private Environment env;

    private ObjectMapper objectMapper;

    @Autowired
    private WebTestClient client;

    private Long userIdTest1;

    private Long userIdTest2;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        userIdTest1 = 1L;
        userIdTest2 = 2L;
    }

    @Test
    @Order(1)
    void testGetById() {
        User user = createUser001();

        client.get()
                .uri("/1")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.username").isEqualTo(user.getUsername())
                .jsonPath("$.email").isEqualTo(user.getEmail())
                .jsonPath("$.enabled").isEqualTo(true)
                .jsonPath("$.roles").isArray()
                .jsonPath("$.roles[0].name").isEqualTo("ROLE_USER");
    }

    private User createUser001() {
        User user = new User();
        List<Role> roles = new ArrayList<>();

        roles.add(new Role(2L, "ROLE_USER"));

        user.setId(1L);
        user.setUsername("alejandro");
        user.setEmail("alejandro@gmail.com");
        user.setPassword("alejandro12345");
        user.setEnabled(true);
        user.setAdmin(false);
        user.setRoles(roles);
        user.setCreatedAt(Timestamp.from(Instant.now()));

        return user;
    }

    @Test
    @Order(2)
    void testGetByUsername() {
        User user = createUser001();

        client.get()
                .uri("/username/alejandro")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$").isNotEmpty()
                .jsonPath("$.id").isEqualTo(user.getId())
                .jsonPath("$.username").isEqualTo(user.getUsername())
                .jsonPath("$.enabled").isEqualTo(true)
                .jsonPath("$.roles").isArray()
                .jsonPath("$.roles").isNotEmpty()
                .jsonPath("$.roles[0].name").isEqualTo("ROLE_USER");
    }

    @Test
    @Order(3)
    void testGetByEmail() {
        User user = createUser001();

        client.get()
                .uri("/email/alejandro@gmail.com")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$").isNotEmpty()
                .jsonPath("$.id").isEqualTo(user.getId())
                .jsonPath("$.username").isEqualTo(user.getUsername())
                .jsonPath("$.email").isEqualTo(user.getEmail())
                .jsonPath("$.enabled").isEqualTo(true)
                .jsonPath("$.roles").isNotEmpty()
                .jsonPath("$.roles").isArray()
                .jsonPath("$.roles[0].name").isEqualTo("ROLE_USER");
    }

    @Test
    @Order(4)
    void testGetAllUsers() {
        User user1 = createUser001();
        User user2 = createUser002();

        client.get()
                .uri("/")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$[0]").isNotEmpty()
                .jsonPath("$").isArray()
                .jsonPath("$[1]").isNotEmpty()
                .jsonPath("$[0].id").isEqualTo(user1.getId())
                .jsonPath("$[1].id").isEqualTo(user2.getId())
                .jsonPath("$[0].username").isEqualTo(user1.getUsername())
                .jsonPath("$[1].username").isEqualTo(user2.getUsername())
                .jsonPath("$[0].enabled").isEqualTo(true)
                .jsonPath("$[1].enabled").isEqualTo(true)
                .jsonPath("$[0].roles").isArray()
                .jsonPath("$[1].roles").isArray()
                .jsonPath("$[0].roles").isNotEmpty()
                .jsonPath("$[1].roles").isNotEmpty();
    }

    private User createUser002() {
        User user = new User();
        List<Role> roles = new ArrayList<>();

        roles.add(new Role(2L, "ROLE_USER"));

        user.setId(2L);
        user.setUsername("ester");
        user.setEmail("ester@gmail.com");
        user.setPassword("ester12345");
        user.setEnabled(true);
        user.setAdmin(false);
        user.setRoles(roles);
        user.setCreatedAt(Timestamp.from(Instant.now()));

        return user;
    }

    @Test
    @Order(5)
    void testRegister() {
        UserRegisterDto userRegisterRequest = new UserRegisterDto();
        userRegisterRequest.setUsername("mateo");
        userRegisterRequest.setEmail("mateo@gmail.com");
        userRegisterRequest.setPassword("mateo12345");

        client.post()
                .uri("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userRegisterRequest)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .consumeWith(res -> {
                    try {
                        JsonNode json = objectMapper.readTree(res.getResponseBody());
                        assertAll(
                            () -> assertNotNull(json),
                            () -> assertEquals("mateo", json.path("username").asText()),
                            () -> assertNotNull(json.path("password").asText()),
                            () -> assertTrue(json.path("enabled").asBoolean()),
                            () -> assertFalse(json.path("admin").asBoolean()),
                            () -> assertNotNull(json.path("roles"))
                            // () -> assertEquals("ROLE_USERR", json.path("roles"))
                        );
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }

    @Test
    void testProfile() {
        assertArrayEquals(new String[]{"test"}, env.getActiveProfiles());
    }

    @Test
    void testApplicationPropertiesFile() {
        assertEquals("jdbc:h2:mem:public", env.getProperty("spring.datasource.url"));
    }

}
