package com.ricardo.scalable.ecommerce.platform.userService.integrationTests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import org.springframework.test.web.reactive.server.WebTestClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ricardo.scalable.ecommerce.platform.userService.entities.Role;
import com.ricardo.scalable.ecommerce.platform.userService.entities.User;
import com.ricardo.scalable.ecommerce.platform.userService.repositories.dto.UserRegisterDto;
import com.ricardo.scalable.ecommerce.platform.userService.repositories.dto.UserUpdateInfoDto;
import com.ricardo.scalable.ecommerce.platform.userService.repositories.dto.UserUpdatePasswordDto;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.*;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

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

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
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
                            () -> assertEquals(4L, json.path("id").asLong()),
                            () -> assertNotNull(json),
                            () -> assertEquals("mateo", json.path("username").asText()),
                            () -> assertNotNull(json.path("password").asText()),
                            () -> assertTrue(json.path("enabled").asBoolean()),
                            () -> assertFalse(json.path("admin").asBoolean()),
                            () -> assertNotNull(json.path("roles"))
                        );
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }

    @Test
    @Order(6)
    void testCreateUser() {
        UserRegisterDto userRegisterRequest = new UserRegisterDto();
        userRegisterRequest.setUsername("pepa");
        userRegisterRequest.setEmail("pepa@gmail.com");
        userRegisterRequest.setPassword("pepa12345");

        client.post()
                .uri("/")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userRegisterRequest)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .consumeWith(response -> {
                    try {
                        JsonNode json = objectMapper.readTree(response.getResponseBody());
                        assertAll(
                            () -> assertEquals(5L, json.path("id").asLong()),
                            () -> assertNotNull(json),
                            () -> assertEquals("pepa", json.path("username").asText()),
                            () -> assertNotNull(json.path("password").asText()),
                            () -> assertTrue(json.path("enabled").asBoolean()),
                            () -> assertFalse(json.path("admin").asBoolean()),
                            () -> assertNotNull(json.path("roles"))
                        );
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
    }

    @Test
    @Order(7)
    void testUpdateUser() {
        UserUpdateInfoDto userUpdateRequest = new UserUpdateInfoDto();
        userUpdateRequest.setUsername("pepona");
        userUpdateRequest.setEmail("pepona@gmail.com");
        userUpdateRequest.setEnabled(false);

        client.put()
                .uri("/5")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userUpdateRequest)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .consumeWith(response -> {
                   try {
                        JsonNode json = objectMapper.readTree(response.getResponseBody());
                        assertAll(
                            () -> assertNotNull(json),
                            () -> assertEquals(5L, json.path("id").asLong()),
                            () -> assertEquals("pepona", json.path("username").asText()),
                            () -> assertNotNull(json.path("password").asText()),
                            () -> assertFalse(json.path("enabled").asBoolean()),
                            () -> assertFalse(json.path("admin").asBoolean()),
                            () -> assertNotNull(json.path("roles"))
                        );
                   } catch (IOException ex) {
                    ex.printStackTrace();
                   } 
                });
    }

    @Test
    @Order(8)
    void testChangePassword() {
        UserUpdatePasswordDto userUpdatePasswordRequest = new UserUpdatePasswordDto();
        userUpdatePasswordRequest.setOldPassword("pepa12345");
        userUpdatePasswordRequest.setNewPassword("pepona12345");

        client.put()
                .uri("/change-password/5")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userUpdatePasswordRequest)
                .exchange()
                .expectStatus().isNoContent();

        UserUpdatePasswordDto userUpdatePasswordUnauthorizedRequest = new UserUpdatePasswordDto();
        userUpdatePasswordUnauthorizedRequest.setOldPassword("pepa12345");
        userUpdatePasswordUnauthorizedRequest.setNewPassword("pepona12345");

        client.put()
                .uri("/change-password/5")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userUpdatePasswordUnauthorizedRequest)
                .exchange()
                .expectStatus().isUnauthorized();

        UserUpdatePasswordDto userUpdatePasswordBadRequest = new UserUpdatePasswordDto();
        userUpdatePasswordBadRequest.setOldPassword("");
        userUpdatePasswordBadRequest.setNewPassword("");

        client.put()
                .uri("/change-password/5")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userUpdatePasswordBadRequest)
                .exchange()
                .expectStatus().isBadRequest();

        String notExistingUserId = "50";

        client.put()
                .uri("/change-password/" + notExistingUserId)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userUpdatePasswordRequest)
                .exchange()
                .expectStatus().isNotFound();

        UserUpdatePasswordDto userUpdatePasswordEmpty = new UserUpdatePasswordDto();

        client.put()
                .uri("/change-password/2")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userUpdatePasswordEmpty)
                .exchange()
                .expectStatus().is5xxServerError();
    }
    
    @Test
    @Order(9)
    void testChangeUserRoles() {
        User user = new User();
        Role adminRole = new Role(1L, "ROLE_ADMIN");
        Role sellerRole = new Role(3L, "ROLE_SELLER");
        List<Role> roles = List.of(adminRole, sellerRole);

        user.setRoles(roles);

        client.put()
                .uri("/roles/1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(user)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .consumeWith(response -> {
                    try {
                        JsonNode json = objectMapper.readTree(response.getResponseBody());
                        assertAll(
                            () -> assertNotNull(json),
                            () -> assertEquals(1L, json.path("id").asLong()),
                            () -> assertEquals("alejandro", json.path("username").asText()),
                            () -> assertTrue(json.path("enabled").asBoolean()),
                            () -> assertTrue(json.path("admin").asBoolean()),
                            () -> assertNotNull(json.path("roles")),
                            () -> assertEquals(3, json.path("roles").size())
                        );
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });

        User userWithoutRoles = new User();

        client.put()
                .uri("/roles/2")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userWithoutRoles)
                .exchange()
                .expectStatus().is5xxServerError();

        client.put()
                .uri("/roles/50")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(user)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(10)
    void testBlockUser() {
        client.put()
                .uri("/block/3")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .consumeWith(response -> {
                    try {
                        JsonNode json = objectMapper.readTree(response.getResponseBody());
                        assertAll(
                            () -> assertNotNull(json),
                            () -> assertEquals(3L, json.path("id").asLong()),
                            () -> assertEquals("pepe", json.path("username").asText()),
                            () -> assertFalse(json.path("enabled").asBoolean())
                        );
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });

        client.put()
                .uri("/block/50")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(11)
    void testUnlockUser() {
        client.put()
                .uri("/unlock/3")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .consumeWith(response -> {
                    try {
                        JsonNode json = objectMapper.readTree(response.getResponseBody());
                        assertAll(
                            () -> assertNotNull(json),
                            () -> assertEquals(3L, json.path("id").asLong()),
                            () -> assertEquals("pepe", json.path("username").asText()),
                            () -> assertTrue(json.path("enabled").asBoolean())
                        );
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });

        client.put()
                .uri("/unlock/50")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(12)
    void testDelete() {
        client.delete()
                .uri("/5")
                .exchange()
                .expectStatus().isNoContent();

        client.get()
                .uri("/")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .consumeWith(response -> {
                    try {
                        JsonNode json = objectMapper.readTree(response.getResponseBody());
                        assertAll(
                            () -> assertEquals(4, json.size())
                        );
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });

        client.get()
                .uri("/5")
                .exchange()
                .expectStatus().isNotFound();
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
