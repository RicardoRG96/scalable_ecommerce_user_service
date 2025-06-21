package com.ricardo.scalable.ecommerce.platform.userService.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ricardo.scalable.ecommerce.platform.libs_common.entities.User;
import com.ricardo.scalable.ecommerce.platform.userService.exceptions.VerificationTokenNotFoundException;
import com.ricardo.scalable.ecommerce.platform.userService.model.repositories.UserRepository;

@Service
public class VerificationTokenService {

    @Autowired
    private UserRepository userRepository;

    public void createVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        user.setVerificationToken(token);
    }

    public void validateVerificationToken(String token) {
        User user = userRepository.findByVerificationToken(token)
                .orElseThrow(() -> new VerificationTokenNotFoundException("Invalid verification token"));
    
        user.setEnabled(true);
        userRepository.save(user);
    }

}
