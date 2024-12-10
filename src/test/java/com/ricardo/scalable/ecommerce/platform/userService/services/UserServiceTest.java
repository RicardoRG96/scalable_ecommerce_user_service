package com.ricardo.scalable.ecommerce.platform.userService.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.ricardo.scalable.ecommerce.platform.userService.Data;
import com.ricardo.scalable.ecommerce.platform.userService.entities.Role;
import com.ricardo.scalable.ecommerce.platform.userService.entities.User;
import com.ricardo.scalable.ecommerce.platform.userService.exceptions.PasswordDoNotMatchException;

import org.junit.jupiter.api.Test;

import com.ricardo.scalable.ecommerce.platform.userService.repositories.RoleRepository;
import com.ricardo.scalable.ecommerce.platform.userService.repositories.UserRepository;
import com.ricardo.scalable.ecommerce.platform.userService.repositories.dto.UserRegisterDto;
import com.ricardo.scalable.ecommerce.platform.userService.repositories.dto.UserUpdateInfoDto;
import com.ricardo.scalable.ecommerce.platform.userService.repositories.dto.UserUpdatePasswordDto;

@SpringBootTest
public class UserServiceTest {

    @MockitoBean
    UserRepository userRepository;

    @MockitoBean
    RoleRepository roleRepository;

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    void testFindById() {
        when(userRepository.findById(1L)).thenReturn(Data.createUser001());
        when(userRepository.findById(2L)).thenReturn(Data.createUser002());

        Optional<User> user1 = userService.findById(1L);
        Optional<User> user2 = userService.findById(2L);

        assertTrue(user1.isPresent());
        assertTrue(user2.isPresent());
        assertEquals("ricardo", user1.orElseThrow().getUsername());
        assertEquals("mateo", user2.orElseThrow().getUsername());
        assertEquals(1, user1.orElseThrow().getRoles().size());
        assertEquals(1, user2.orElseThrow().getRoles().size());
        assertEquals("ROLE_USER", user1.orElseThrow().getRoles().getFirst().getName());
        assertEquals("ROLE_USER", user2.orElseThrow().getRoles().getFirst().getName());

        verify(userRepository).findById(1L);
        verify(userRepository).findById(2L);
    }

    @Test
    void testFindByUsername() {
        when(userRepository.findByUsername("ricardo")).thenReturn(Data.createUser001());
        when(userRepository.findByUsername("mateo")).thenReturn(Data.createUser002());

        Optional<User> user1 = userService.findByUsername("ricardo");
        Optional<User> user2 = userService.findByUsername("mateo");

        assertTrue(user1.isPresent());
        assertTrue(user2.isPresent());
        assertEquals("ricardo", user1.orElseThrow().getUsername());
        assertEquals("mateo", user2.orElseThrow().getUsername());
        assertEquals("ricardo@gmail.com", user1.orElseThrow().getEmail());
        assertEquals("mateo@gmail.com", user2.orElseThrow().getEmail());
        assertEquals(1, user1.orElseThrow().getRoles().size());
        assertEquals(1, user2.orElseThrow().getRoles().size());
        assertEquals("ROLE_USER", user1.orElseThrow().getRoles().getFirst().getName());
        assertEquals("ROLE_USER", user2.orElseThrow().getRoles().getFirst().getName());

        verify(userRepository).findByUsername("ricardo");
        verify(userRepository).findByUsername("mateo");
    }

    @Test
    void testFindByEmail() {
        when(userRepository.findByEmail("ricardo@gmail.com")).thenReturn(Data.createUser001());
        when(userRepository.findByEmail("mateo@gmail.com")).thenReturn(Data.createUser002());

        Optional<User> user1 = userService.findByEmail("ricardo@gmail.com");
        Optional<User> user2 = userService.findByEmail("mateo@gmail.com");

        assertTrue(user1.isPresent());
        assertTrue(user2.isPresent());
        assertEquals("ricardo", user1.orElseThrow().getUsername());
        assertEquals("mateo", user2.orElseThrow().getUsername());
        assertEquals("ricardo@gmail.com", user1.orElseThrow().getEmail());
        assertEquals("mateo@gmail.com", user2.orElseThrow().getEmail());
        assertEquals(1, user1.orElseThrow().getRoles().size());
        assertEquals(1, user2.orElseThrow().getRoles().size());
        assertEquals("ROLE_USER", user1.orElseThrow().getRoles().getFirst().getName());
        assertEquals("ROLE_USER", user2.orElseThrow().getRoles().getFirst().getName());

        verify(userRepository).findByEmail("ricardo@gmail.com");
        verify(userRepository).findByEmail("mateo@gmail.com");
    }

    @Test
    void testFindAll() {
        when(userRepository.findAll()).thenReturn(Data.createListOfUsers());

        List<User> users = (List<User>) userService.findAll();

        assertNotNull(users);
        assertEquals(2, users.size());
        assertEquals("ricardo", users.get(0).getUsername());
        assertEquals("mateo", users.get(1).getUsername());
        assertEquals("ricardo@gmail.com", users.get(0).getEmail());
        assertEquals("mateo@gmail.com", users.get(1).getEmail());
        assertTrue(users.get(0).isEnabled());
        assertTrue(users.get(1).isEnabled());
        assertEquals(1, users.get(0).getRoles().size());
        assertEquals(1, users.get(1).getRoles().size());
        assertEquals("ROLE_USER", users.get(0).getRoles().getFirst().getName());
        assertEquals("ROLE_USER", users.get(1).getRoles().getFirst().getName());

        verify(userRepository).findAll();
    }

    @Test
    void testSave() {
        Role role = new Role(1L, "ROLE_USER");
        when(userRepository.save(any())).thenReturn(Data.createUser001().orElseThrow());
        when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.of(role));

        UserRegisterDto userRegisterDto = new UserRegisterDto();
        userRegisterDto.setUsername("ricardo");
        userRegisterDto.setEmail("ricardo@gmail.com");
        userRegisterDto.setPassword("ricardo12345");
        userRegisterDto.setAdmin(false);

        User createdUser = userService.save(userRegisterDto);

        assertAll(
            () -> assertNotNull(createdUser),
            () -> assertEquals("ricardo", createdUser.getUsername()),
            () -> assertEquals("ricardo@gmail.com", createdUser.getEmail()),
            () -> assertFalse(createdUser.isAdmin()),
            () -> assertTrue(createdUser.isEnabled()),
            () -> assertEquals("ROLE_USER", createdUser.getRoles().getFirst().getName())
        );

        verify(userRepository).save(any());
    }

    @Test
    void testUpdate() {
        User userUpdated = Data.createUser001().orElseThrow();
        userUpdated.setUsername("ricardo2");
        userUpdated.setEmail("ricardo@correo.cl");
        userUpdated.setEnabled(false);

        when(userRepository.findById(1L)).thenReturn(Data.createUser001());
        when(userRepository.save(any())).thenReturn(userUpdated);

        UserUpdateInfoDto userUpdateInfoDto = 
            new UserUpdateInfoDto("ricardo2", "ricardo@correo.cl", false);

        Optional<User> userUpdatedOptional = userService.update(userUpdateInfoDto, 1L);

        assertNotNull(userUpdatedOptional);
        assertEquals("ricardo2", userUpdatedOptional.orElseThrow().getUsername());
        assertEquals("ricardo@correo.cl", userUpdatedOptional.orElseThrow().getEmail());
        assertEquals(false, userUpdatedOptional.orElseThrow().isEnabled());

    }

    @Test
    void testUpdatePassword() throws PasswordDoNotMatchException {
        User userUpdatedResponse = Data.createUser001().orElseThrow();
        userUpdatedResponse.setPassword(passwordEncoder.encode("ricardo123456"));

        User user = Data.createUser001().orElseThrow();
        user.setPassword(passwordEncoder.encode("ricardo12345"));

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any())).thenReturn(userUpdatedResponse);

        UserUpdatePasswordDto userUpdatePasswordDto = 
            new UserUpdatePasswordDto("ricardo12345", "ricardo123456");
        
        Optional<User> userOptional = userService.updatePassword(userUpdatePasswordDto, 1L);

        assertTrue(userOptional.isPresent());
        assertTrue(passwordEncoder.matches("ricardo123456", userOptional.orElseThrow().getPassword()));

    }

    @Test
    void testUpdatePasswordThrowsException() {
        User userUpdatedResponse = Data.createUser001().orElseThrow();
        userUpdatedResponse.setPassword(passwordEncoder.encode("ricardo123456"));

        User user = Data.createUser001().orElseThrow();
        user.setPassword(passwordEncoder.encode("ricardo12345"));

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any())).thenReturn(userUpdatedResponse);

        assertThrows(PasswordDoNotMatchException.class, () -> {
            UserUpdatePasswordDto userUpdatePassword = 
                new UserUpdatePasswordDto("ricardo", "ricardo123456");

            userService.updatePassword(userUpdatePassword, 1L);
        });
    }

    @Test
    void testAddUserRoles() {
        User userUpdatedResponse = Data.createUser001().orElseThrow();
        Role addedRole1 = new Role(2L, "ROLE_ADMIN");
        Role addedRole2 = new Role(3L, "ROLE_SELLER");

        userUpdatedResponse.getRoles().add(addedRole1);
        userUpdatedResponse.getRoles().add(addedRole2);

        when(userRepository.findById(1L)).thenReturn(Data.createUser001());
        when(userRepository.save(any())).thenReturn(userUpdatedResponse);

        User userUpdateRequest = new User();
        userUpdateRequest.setRoles(
            List.of(
                new Role(2L, "ROLE_ADMIN"),
                new Role(3L, "ROLE_SELLER")
            )
        );
        Optional<User> userOptional = userService.addUserRoles(userUpdateRequest, 1L);
        List<String> userOptionalRoles = userOptional
                .orElseThrow()
                .getRoles()
                .stream()
                .map(role -> role.getName())
                .collect(Collectors.toList());

        assertTrue(userOptional.isPresent());
        assertEquals(3, userOptional.orElseThrow().getRoles().size());
        assertTrue(userOptionalRoles.contains("ROLE_ADMIN"));
        assertTrue(userOptionalRoles.contains("ROLE_SELLER"));
        assertTrue(userOptionalRoles.contains("ROLE_USER"));
    }

    @Test
    void testBlockUser() {
        User mockBlockedUserResponse = Data.createUser001().orElseThrow();
        mockBlockedUserResponse.setEnabled(false);

        when(userRepository.findById(1L)).thenReturn(Data.createUser001());
        when(userRepository.save(any())).thenReturn(mockBlockedUserResponse);

        Optional<User> userOptionalRequest = userService.blockUser(1L);

        assertTrue(userOptionalRequest.isPresent());
        assertEquals("ricardo", userOptionalRequest.orElseThrow().getUsername());
        assertFalse(userOptionalRequest.orElseThrow().isEnabled());
    }

    @Test
    void testUnlockUser() {
        User mockUserResponse = Data.createUser001().orElseThrow();
        mockUserResponse.setEnabled(true);

        when(userRepository.findById(1L)).thenReturn(Data.createUser001());
        when(userRepository.save(any())).thenReturn(mockUserResponse);

        Optional<User> userOptionalRequest = userService.unlockUser(1L);

        assertTrue(userOptionalRequest.isPresent());
        assertEquals("ricardo", userOptionalRequest.orElseThrow().getUsername());
        assertTrue(userOptionalRequest.orElseThrow().isEnabled());
    }
}   
