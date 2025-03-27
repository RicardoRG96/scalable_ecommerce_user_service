package com.ricardo.scalable.ecommerce.platform.userService.integrationTests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import org.springframework.test.web.reactive.server.WebTestClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ricardo.scalable.ecommerce.platform.libs_common.entities.Role;
import com.ricardo.scalable.ecommerce.platform.libs_common.entities.User;
import com.ricardo.scalable.ecommerce.platform.userService.repositories.dto.UserRegisterDto;
import com.ricardo.scalable.ecommerce.platform.userService.repositories.dto.UserUpdateInfoDto;
import com.ricardo.scalable.ecommerce.platform.userService.repositories.dto.UserUpdatePasswordDto;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.*;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static com.ricardo.scalable.ecommerce.platform.userService.services.testData.UserControllerTestData.*;

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

    @Test
    @Order(2)
    void testGetByIdNotFound() {
        String notExistingUserId = "50";

        client.get()
                .uri("/" + notExistingUserId)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(3)
    void testGetByUsername() {
        User user = createUser001();

        client.get()
                .uri("/username/alejandro10")
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
    @Order(4)
    void testGetByUsernameNotFound() {
        String notExistingUsername = "example";

        client.get()
                .uri("/username/" + notExistingUsername)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(5)
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
    @Order(6)
    void testGetByEmailNotFound() {
        String notExistingEmail = "notExisting@example.com";

        client.get()
                .uri("/email/"+ notExistingEmail)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(7)
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

    @Test
    @Order(8)
    void testRegister() {
        UserRegisterDto userRegisterRequest = createUserRegisterDto001();

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
                            () -> assertEquals("avatar4.png", json.path("avatar").asText()),
                            () -> assertEquals("mateo", json.path("firstName").asText()),
                            () -> assertEquals("retamal", json.path("lastName").asText()),
                            () -> assertEquals("mateo10", json.path("username").asText()),
                            () -> assertNotNull(json.path("password").asText()),
                            () -> assertEquals("+56912345678", json.path("phoneNumber").asText()),
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
    @Order(9)
    void testRegisterBadRequest() {
        UserRegisterDto userRegisterBadRequest = new UserRegisterDto();

        client.post()
                .uri("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userRegisterBadRequest)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    @Order(10)
    void testCreateUser() {
        UserRegisterDto userRegisterRequest = createUserRegisterDto002();

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
                            () -> assertEquals("avatar5.png", json.path("avatar").asText()),
                            () -> assertEquals("pepa", json.path("firstName").asText()),
                            () -> assertEquals("pepona", json.path("lastName").asText()),
                            () -> assertEquals("pepa25", json.path("username").asText()),
                            () -> assertNotNull(json.path("password").asText()),
                            () -> assertEquals("+56998765432", json.path("phoneNumber").asText()),
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
    @Order(11)
    void testCreateUserBadRequest() {
        UserRegisterDto userRegisterBadRequest = new UserRegisterDto();

        client.post()
                .uri("/")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userRegisterBadRequest)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    @Order(12)
    void testUpdateUser() {
        UserUpdateInfoDto userUpdateRequest = createUserUpdateInfoDto();

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
                            () -> assertEquals("avatar6.png", json.path("avatar").asText()),
                            () -> assertEquals("pepa", json.path("firstName").asText()),
                            () -> assertEquals("pepona", json.path("lastName").asText()),
                            () -> assertEquals("pepona1500", json.path("username").asText()),
                            () -> assertNotNull(json.path("password").asText()),
                            () -> assertEquals("+56998768567", json.path("phoneNumber").asText()),
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
    @Order(13)
    void testUpdateUserBadRequest() {
        UserUpdateInfoDto userUpdateInfoBadRequest = new UserUpdateInfoDto();
        userUpdateInfoBadRequest.setUsername("");
        userUpdateInfoBadRequest.setEmail("example.com");
        userUpdateInfoBadRequest.setEnabled(true);

        client.put()
                .uri("/5")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userUpdateInfoBadRequest)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    @Order(14)
    void testUpdateUserNotFound() {
        UserUpdateInfoDto userUpdateRequest = createUserUpdateInfoDto();
        String notExistingUserId = "50";

        client.put()
                .uri("/" + notExistingUserId)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userUpdateRequest)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(15)
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
    }
    
    @Test
    @Order(16)
    void testChangePasswordUnauthorized() {
        UserUpdatePasswordDto userUpdatePasswordUnauthorizedRequest = new UserUpdatePasswordDto();
        userUpdatePasswordUnauthorizedRequest.setOldPassword("pepa12345");
        userUpdatePasswordUnauthorizedRequest.setNewPassword("pepona12345");

        client.put()
                .uri("/change-password/5")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userUpdatePasswordUnauthorizedRequest)
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    @Order(17)
    void testChangePasswordBadRequest() {
        UserUpdatePasswordDto userUpdatePasswordBadRequest = new UserUpdatePasswordDto();
        userUpdatePasswordBadRequest.setOldPassword("");
        userUpdatePasswordBadRequest.setNewPassword("");

        client.put()
                .uri("/change-password/5")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userUpdatePasswordBadRequest)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    @Order(18)
    void testChangePasswordNotFound() {
        UserUpdatePasswordDto userUpdatePasswordRequest = new UserUpdatePasswordDto();
        userUpdatePasswordRequest.setOldPassword("pepa12345");
        userUpdatePasswordRequest.setNewPassword("pepona12345");
        String notExistingUserId = "50";

        client.put()
                .uri("/change-password/" + notExistingUserId)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userUpdatePasswordRequest)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(19)
    void testChangePasswordInternalServerError() {
        UserUpdatePasswordDto userUpdatePasswordWithNullValues = new UserUpdatePasswordDto();

        client.put()
                .uri("/change-password/2")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userUpdatePasswordWithNullValues)
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    @Order(20)
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
                            () -> assertEquals("avatar1.png", json.path("avatar").asText()),
                            () -> assertEquals("alejandro", json.path("firstName").asText()),
                            () -> assertEquals("retamal", json.path("lastName").asText()),
                            () -> assertEquals("alejandro10", json.path("username").asText()),
                            () -> assertTrue(json.path("enabled").asBoolean()),
                            () -> assertTrue(json.path("admin").asBoolean()),
                            () -> assertNotNull(json.path("roles")),
                            () -> assertEquals(3, json.path("roles").size())
                        );
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
    }

    @Test
    @Order(21)
    void testChangeUserRolesInternalServerError() {
        User userWithoutRoles = new User();

        client.put()
                .uri("/roles/2")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userWithoutRoles)
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    @Order(22)
    void testChangeUserRolesNotFound() {
        User user = new User();
        Role adminRole = new Role(1L, "ROLE_ADMIN");
        Role sellerRole = new Role(3L, "ROLE_SELLER");
        List<Role> roles = List.of(adminRole, sellerRole);

        user.setRoles(roles);
        
        client.put()
                .uri("/roles/50")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(user)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(23)
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
                            () -> assertEquals("avatar3.png", json.path("avatar").asText()),
                            () -> assertEquals("pepe", json.path("firstName").asText()),
                            () -> assertEquals("pepon", json.path("lastName").asText()),
                            () -> assertEquals("pepe58", json.path("username").asText()),
                            () -> assertFalse(json.path("enabled").asBoolean())
                        );
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
    }

    @Test
    @Order(24)
    void testBlockUserNotFound() {
        client.put()
                .uri("/block/50")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(25)
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
                            () -> assertEquals("avatar3.png", json.path("avatar").asText()),
                            () -> assertEquals("pepe", json.path("firstName").asText()),
                            () -> assertEquals("pepon", json.path("lastName").asText()),
                            () -> assertEquals("pepe58", json.path("username").asText()),
                            () -> assertTrue(json.path("enabled").asBoolean())
                        );
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
    }

    @Test
    @Order(26)
    void testUnlockUserNotFound() {
        client.put()
                .uri("/unlock/50")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(27)
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
    }

    @Test
    @Order(28)
    void testGetDeletedUser() {
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
        assertEquals("jdbc:h2:mem:public;NON_KEYWORDS=value", env.getProperty("spring.datasource.url"));
    }

}
