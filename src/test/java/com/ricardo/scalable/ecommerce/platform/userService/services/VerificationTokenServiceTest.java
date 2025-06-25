package com.ricardo.scalable.ecommerce.platform.userService.services;

import static com.ricardo.scalable.ecommerce.platform.userService.services.testData.UserServiceTestData.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.ricardo.scalable.ecommerce.platform.libs_common.entities.User;
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

}
