package com.ricardo.scalable.ecommerce.platform.userService.services.testData;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.ricardo.scalable.ecommerce.platform.userService.entities.Role;
import com.ricardo.scalable.ecommerce.platform.userService.entities.User;
import com.ricardo.scalable.ecommerce.platform.userService.repositories.dto.UserRegisterDto;
import com.ricardo.scalable.ecommerce.platform.userService.repositories.dto.UserUpdateInfoDto;

public class UserControllerTestData {

    public static User createUser001() {
        User user = new User();
        List<Role> roles = new ArrayList<>();

        roles.add(new Role(2L, "ROLE_USER"));

        user.setId(1L);
        user.setAvatar("avatar1.png");
        user.setFirstName("alejandro");
        user.setLastName("retamal");
        user.setUsername("alejandro10");
        user.setEmail("alejandro@gmail.com");
        user.setPassword("alejandro12345");
        user.setBirthDate(LocalDate.of(1996, 4, 10));
        user.setPhoneNumber("+56952419637");
        user.setEnabled(true);
        user.setAdmin(false);
        user.setRoles(roles);
        user.setCreatedAt(Timestamp.from(Instant.now()));

        return user;
    }

    public static User createUser002() {
        User user = new User();
        List<Role> roles = new ArrayList<>();

        roles.add(new Role(2L, "ROLE_USER"));

        user.setId(2L);
        user.setAvatar("avatar2.png");
        user.setFirstName("ester");
        user.setLastName("guevara");
        user.setUsername("ester17");
        user.setEmail("ester@gmail.com");
        user.setPassword("ester12345");
        user.setBirthDate(LocalDate.of(1994, 1, 17));
        user.setPhoneNumber("+56932189745");
        user.setEnabled(true);
        user.setAdmin(false);
        user.setRoles(roles);
        user.setCreatedAt(Timestamp.from(Instant.now()));

        return user;
    }

    public static UserRegisterDto createUserRegisterDto001() {
        UserRegisterDto userRegisterDto = new UserRegisterDto();
        userRegisterDto.setAvatar("avatar4.png");
        userRegisterDto.setFirstName("mateo");
        userRegisterDto.setLastName("retamal");
        userRegisterDto.setUsername("mateo10");
        userRegisterDto.setEmail("mateo@gmail.com");
        userRegisterDto.setPassword("mateo12345");
        userRegisterDto.setBirthDate(LocalDate.of(2024, 9, 1));
        userRegisterDto.setPhoneNumber("+56912345678");

        return userRegisterDto;
    }

    public static UserRegisterDto createUserRegisterDto002() {
        UserRegisterDto userRegisterDto = new UserRegisterDto();
        userRegisterDto.setAvatar("avatar5.png");
        userRegisterDto.setFirstName("pepa");
        userRegisterDto.setLastName("pepona");
        userRegisterDto.setUsername("pepa25");
        userRegisterDto.setEmail("pepa@gmail.com");
        userRegisterDto.setPassword("pepa12345");
        userRegisterDto.setBirthDate(LocalDate.of(1990, 5, 10));
        userRegisterDto.setPhoneNumber("+56998765432");

        return userRegisterDto;
    }

    public static UserUpdateInfoDto createUserUpdateInfoDto() {
        UserUpdateInfoDto userUpdateInfoDto = new UserUpdateInfoDto();
        userUpdateInfoDto.setAvatar("avatar6.png");
        userUpdateInfoDto.setFirstName("pepa");
        userUpdateInfoDto.setLastName("pepona");
        userUpdateInfoDto.setUsername("pepona1500");
        userUpdateInfoDto.setEmail("pepona@gmail.com");
        userUpdateInfoDto.setBirthDate(LocalDate.of(1990, 5, 10));
        userUpdateInfoDto.setPhoneNumber("+56998768567");
        userUpdateInfoDto.setEnabled(false);

        return userUpdateInfoDto;
    }

}
