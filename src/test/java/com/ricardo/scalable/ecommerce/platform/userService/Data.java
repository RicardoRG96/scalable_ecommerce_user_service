package com.ricardo.scalable.ecommerce.platform.userService;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ricardo.scalable.ecommerce.platform.userService.entities.Role;
import com.ricardo.scalable.ecommerce.platform.userService.entities.User;

public class Data {

    public static List<User> createListOfUsers() {
        User user1 = createUser001().orElseThrow();
        User user2 = createUser002().orElseThrow();

        return List.of(user1, user2);
    }

    public static Optional<User> createUser001() {
        // List<User> users = createUsers();
        // return users
        //     .stream()
        //     .filter(user -> user.getId() == 1L)
        //     .findFirst();
        User user1 = new User();
        List<Role> roles = new ArrayList<>();
        Role role = new Role();

        role.setId(1L);
        role.setName("ROLE_USER");

        roles.add(role);

        user1.setId(1L);
        user1.setUsername("ricardo");
        user1.setEmail("ricardo@gmail.com");
        user1.setPassword("ricardo12345");
        user1.setEnabled(true);
        user1.setAdmin(false);
        user1.setRoles(roles);
        user1.setCreatedAt(Timestamp.from(Instant.now()));

        return Optional.of(user1);
    }

    public static Optional<User> createUser002() {
        // List<User> users = createUsers();
        // return users
        //     .stream()
        //     .filter(user -> user.getId() == 1L)
        //     .findFirst();
        User user = new User();
        List<Role> roles = new ArrayList<>();
        Role role = new Role();

        role.setId(2L);
        role.setName("ROLE_USER");

        roles.add(role);

        user.setId(1L);
        user.setUsername("mateo");
        user.setEmail("mateo@gmail.com");
        user.setPassword("mateo12345");
        user.setEnabled(true);
        user.setAdmin(false);
        user.setRoles(roles);
        user.setCreatedAt(Timestamp.from(Instant.now()));

        return Optional.of(user);
    }

    public static User userCreated() {
        return createUser001().orElseThrow();
    }

}
