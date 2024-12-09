package com.ricardo.scalable.ecommerce.platform.userService.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import com.ricardo.scalable.ecommerce.platform.userService.Data;
import com.ricardo.scalable.ecommerce.platform.userService.entities.User;

import org.junit.jupiter.api.Test;

import com.ricardo.scalable.ecommerce.platform.userService.repositories.UserRepository;
import com.ricardo.scalable.ecommerce.platform.userService.repositories.dto.UserRegisterDto;

@SpringBootTest
public class UserServiceTest {

    @MockitoBean
    UserRepository userRepository;

    @Autowired
    UserService userService;

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
        User userRequest = Data.createUser001().orElseThrow();
        when(userRepository.save(userRequest)).thenReturn(Data.userCreated());

        UserRegisterDto userRegisterDto = new UserRegisterDto();
        userRegisterDto.setUsername("ricardo");
        userRegisterDto.setEmail("ricardo@gmail.com");
        userRegisterDto.setPassword("ricardo12345");
        userRegisterDto.setAdmin(false);

        User createdUser = userService.save(userRegisterDto);

        assertAll(
            () -> assertNotNull(createdUser)
            // () -> assertEquals("ricardo", createdUser.getUsername()),
            // () -> assertEquals("ricardo@gmail.com", createdUser.getEmail()),
            // () -> assertTrue(createdUser.isAdmin()),
            // () -> assertTrue(createdUser.isEnabled()),
            // () -> assertEquals("ROLE_USER", createdUser.getRoles().getFirst().getName())
        );

        verify(userRepository).save(userRequest);
    }
}   
