package com.ricardo.scalable.ecommerce.platform.userService.repositories;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.ricardo.scalable.ecommerce.platform.userService.entities.User;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    void testFindById() {
        Optional<User> user1 = userRepository.findById(1L);
        Optional<User> user2 = userRepository.findById(2L);

        // Optional<User> user1 = userRepository.findByUsername("ricardo");
        // Optional<User> user2 = userRepository.findByUsername("mateo");

        assertTrue(user1.isPresent());
        assertTrue(user2.isPresent());

        assertEquals(user1.orElseThrow().getUsername(), "ricardo2");
        assertEquals(user2.orElseThrow().getUsername(), "mateo2");

        assertFalse(user1.orElseThrow().isEnabled());
        assertFalse(user2.orElseThrow().isEnabled());

        assertTrue(user1.orElseThrow().isAdmin());
        assertTrue(user2.orElseThrow().isAdmin());
    }

}
