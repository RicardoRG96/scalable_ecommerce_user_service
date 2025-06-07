package com.ricardo.scalable.ecommerce.platform.userService.repositories;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.ricardo.scalable.ecommerce.platform.libs_common.entities.User;
import com.ricardo.scalable.ecommerce.platform.userService.model.repositories.UserRepository;

@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void findByBirthDate_whenBirthDateExists_thenReturnUsers() {
        Optional<List<User>> users = userRepository.findByBirthDate(LocalDate.of(1996, 10, 04));

        assertAll(
            () -> assertTrue(users.isPresent(), "Users should be present"),
            () -> assertFalse(users.orElseThrow().isEmpty(), "User list should not be empty"),
            () -> assertEquals(1, users.orElseThrow().size(), "There should be one user with the given birth date"),
            () -> assertEquals("alejandro10", users.orElseThrow().get(0).getUsername(), "Username should match")
        );
    }

}
