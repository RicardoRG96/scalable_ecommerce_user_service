package com.ricardo.scalable.ecommerce.platform.userService.services;

import static com.ricardo.scalable.ecommerce.platform.userService.services.testData.UserServiceTestData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.ricardo.scalable.ecommerce.platform.libs_common.entities.User;
import com.ricardo.scalable.ecommerce.platform.userService.exceptions.VerificationTokenNotFoundException;
import com.ricardo.scalable.ecommerce.platform.userService.model.repositories.UserRepository;

@SpringBootTest
public class VerificationTokenServiceTest {

    @MockitoBean
    private UserRepository userRepository;

    @Autowired
    private VerificationTokenService verificationTokenService;

    @Test
    void createVerificationToken_whenUserIsValid_shouldCreateToken() {
        User user = createUser001().orElseThrow();

        verificationTokenService.createVerificationToken(user);

        assertAll(
            () -> assertNotNull(user.getVerificationToken())
        );        
    }

    @Test
    void validateVerificationToken_whenTokenIsValid_shouldEnableUser() {
        User user = createUser001().orElseThrow();
        verificationTokenService.createVerificationToken(user);
        String token = user.getVerificationToken();

        when(userRepository.findByVerificationToken(token)).thenReturn(Optional.of(user));
        when((userRepository.save(user))).thenReturn(user);

        User validatedUser = verificationTokenService.validateVerificationToken(token);

        assertAll(
            () -> assertTrue(validatedUser.isEnabled()),
            () -> assertEquals(1L, validatedUser.getId())
        );
    }

    @Test
    void validateVerificationToken_whenTokenIsInvalid_shouldThrowException() {
        String invalidToken = "invalid-token";

        when(userRepository.findByVerificationToken(invalidToken)).thenReturn(Optional.empty());
          
        Exception exception = assertThrows(VerificationTokenNotFoundException.class, () -> {
            verificationTokenService.validateVerificationToken(invalidToken);
        });
        assertEquals("Invalid verification token", exception.getMessage());
    }

}
