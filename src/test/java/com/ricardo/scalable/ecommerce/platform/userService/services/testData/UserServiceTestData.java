package com.ricardo.scalable.ecommerce.platform.userService.services.testData;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ricardo.scalable.ecommerce.platform.userService.entities.Role;
import com.ricardo.scalable.ecommerce.platform.userService.entities.User;
import com.ricardo.scalable.ecommerce.platform.userService.repositories.dto.UserRegisterDto;
import com.ricardo.scalable.ecommerce.platform.userService.repositories.dto.UserUpdateInfoDto;

public class UserServiceTestData {

    public static List<User> createListOfUsers() {
        User user1 = createUser001().orElseThrow();
        User user2 = createUser002().orElseThrow();

        return List.of(user1, user2);
    }

    public static Optional<User> createUser001() {
        User user1 = new User();
        List<Role> roles = new ArrayList<>();
        Role role = new Role();

        role.setId(1L);
        role.setName("ROLE_USER");

        roles.add(role);

        user1.setId(1L);
        user1.setAvatar("avatar1.jpg");
        user1.setFirstName("Ricardo");
        user1.setLastName("Retamal");
        user1.setUsername("ricardo10");
        user1.setEmail("ricardo@gmail.com");
        user1.setPassword("ricardo12345");
        user1.setBirthDate(LocalDate.of(1996, 4, 10));
        user1.setPhoneNumber("+56912345678");
        user1.setEnabled(true);
        user1.setAdmin(false);
        user1.setRoles(roles);
        user1.setCreatedAt(Timestamp.from(Instant.now()));

        return Optional.of(user1);
    }

    public static Optional<User> createUser002() {
        User user = new User();
        List<Role> roles = new ArrayList<>();
        Role role = new Role();

        role.setId(2L);
        role.setName("ROLE_USER");

        roles.add(role);

        user.setId(1L);
        user.setAvatar("avatar2.jpg");
        user.setFirstName("Mateo");
        user.setLastName("Retamal");
        user.setUsername("mateo10");
        user.setEmail("mateo@gmail.com");
        user.setPassword("mateo12345");
        user.setBirthDate(LocalDate.of(2024, 9, 1));
        user.setPhoneNumber("+56987654321");
        user.setEnabled(true);
        user.setAdmin(false);
        user.setRoles(roles);
        user.setCreatedAt(Timestamp.from(Instant.now()));

        return Optional.of(user);
    }

    public static User userCreated() {
        return createUser001().orElseThrow();
    }

    public static UserRegisterDto createUserRegisterDto() {
        UserRegisterDto userRegisterDto = new UserRegisterDto();

        userRegisterDto.setAvatar("avatar3.jpg");
        userRegisterDto.setFirstName("Carla");
        userRegisterDto.setLastName("Tur");
        userRegisterDto.setUsername("carla17");
        userRegisterDto.setEmail("carla@gmail.com");
        userRegisterDto.setPassword("carla12345");
        userRegisterDto.setBirthDate(LocalDate.of(1994, 1, 17));
        userRegisterDto.setPhoneNumber("+56998963254");

        return userRegisterDto;
    }

    public static User createUserRegisterResponse() {
        User user = new User();
        List<Role> roles = new ArrayList<>();
        Role role = new Role();

        role.setId(1L);
        role.setName("ROLE_USER");

        roles.add(role);

        user.setId(1L);
        user.setAvatar("avatar3.jpg");
        user.setFirstName("Carla");
        user.setLastName("Tur");
        user.setUsername("carla17");
        user.setEmail("carla@gmail.com");
        user.setPassword("carla12345");
        user.setBirthDate(LocalDate.of(1994, 1, 17));
        user.setPhoneNumber("+56998963254");
        user.setEnabled(true);
        user.setAdmin(false);
        user.setRoles(roles);
        user.setCreatedAt(Timestamp.from(Instant.now()));

        return user;
    }

    public static User createUpdatedUser() {
        User user = new User();
        List<Role> roles = new ArrayList<>();
        Role role = new Role();

        role.setId(1L);
        role.setName("ROLE_USER");

        roles.add(role);

        user.setId(1L);
        user.setAvatar("avatar105.jpg");
        user.setFirstName("Ricardo");
        user.setLastName("Guerrero");
        user.setUsername("ricardo2");
        user.setEmail("ricardo@correo.cl");
        user.setPassword("ricardo12345");
        user.setBirthDate(LocalDate.of(1996, 4, 10));
        user.setPhoneNumber("+56912345678");
        user.setEnabled(false);

        return user;
    }

    public static UserUpdateInfoDto createUserUpdateInfoDto() {
        UserUpdateInfoDto userUpdateInfoDto = new UserUpdateInfoDto();

        userUpdateInfoDto.setAvatar("avatar105.jpg");
        userUpdateInfoDto.setFirstName("Ricardo");
        userUpdateInfoDto.setLastName("Guerrero");
        userUpdateInfoDto.setUsername("ricardo2");
        userUpdateInfoDto.setEmail("ricardo@correo.cl");
        userUpdateInfoDto.setBirthDate(LocalDate.of(1996, 4, 10));
        userUpdateInfoDto.setPhoneNumber("+56935405236");
        userUpdateInfoDto.setEnabled(false);

        return userUpdateInfoDto;
    }
}
