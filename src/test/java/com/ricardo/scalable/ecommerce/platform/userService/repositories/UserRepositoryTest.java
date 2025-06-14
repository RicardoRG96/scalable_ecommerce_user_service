package com.ricardo.scalable.ecommerce.platform.userService.repositories;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

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
        List<User> users = userRepository.findByBirthdayMonthAndDay(10, 4);

        assertAll(
            () -> assertNotNull(users, "Users should not be null"),
            () -> assertFalse(users.isEmpty(), "User list should not be empty"),
            () -> assertEquals(1, users.size(), "There should be one user with the given birth date"),
            () -> assertEquals("alejandro10", users.get(0).getUsername(), "Username should match")
        );
    }

}
