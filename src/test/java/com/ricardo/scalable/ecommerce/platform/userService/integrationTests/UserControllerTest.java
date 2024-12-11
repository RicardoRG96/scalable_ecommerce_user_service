package com.ricardo.scalable.ecommerce.platform.userService.integrationTests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ricardo.scalable.ecommerce.platform.userService.entities.Role;
import com.ricardo.scalable.ecommerce.platform.userService.entities.User;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.*;

import java.sql.Timestamp;
import java.time.Instant;
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
// @DataJpaTest
// @TestPropertySource("classpath:application-test.properties")
// @TestPropertySource("/application-test.properties")
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
    void testGetById() throws JsonProcessingException {
        User user = createUser001();

        client.get()
                .uri("http://localhost:8005/1")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.username").isEqualTo(user.getUsername())
                .jsonPath("$.email").isEqualTo(user.getEmail())
                .jsonPath("$.enabled").isEqualTo(true);
                // .json(objectMapper.writeValueAsString(user));
    }

    private User createUser001() {
        User user = new User();
        user.setId(1L);
        user.setUsername("alejandro");
        user.setEmail("alejandro@gmail.com");
        user.setPassword("alejandro12345");
        user.setEnabled(true);
        user.setAdmin(false);
        user.setRoles(List.of(new Role(1L, "ROLE_USER")));
        user.setCreatedAt(Timestamp.from(Instant.now()));

        return user;
    }

    @Test
    void testProfile() {
        assertArrayEquals(new String[]{"test"}, env.getActiveProfiles());
    }

    @Test
    void testApplicationPropertiesFile() {
        assertEquals("ñacson", env.getProperty("spring.datasource.url"));
    }

}
