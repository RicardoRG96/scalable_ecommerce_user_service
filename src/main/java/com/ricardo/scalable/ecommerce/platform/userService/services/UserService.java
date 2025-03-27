package com.ricardo.scalable.ecommerce.platform.userService.services;

import com.ricardo.scalable.ecommerce.platform.libs_common.entities.User;
import com.ricardo.scalable.ecommerce.platform.userService.exceptions.PasswordDoNotMatchException;
import com.ricardo.scalable.ecommerce.platform.userService.repositories.dto.UserRegisterDto;
import com.ricardo.scalable.ecommerce.platform.userService.repositories.dto.UserUpdateInfoDto;
import com.ricardo.scalable.ecommerce.platform.userService.repositories.dto.UserUpdatePasswordDto;

import java.util.Optional;

public interface UserService {

    Optional<User> findById(Long id);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Iterable<User> findAll();

    User save(UserRegisterDto user);

    Optional<User> update(UserUpdateInfoDto userUpdated, Long id);

    Optional<User> updatePassword(UserUpdatePasswordDto userUpdated, Long id) throws PasswordDoNotMatchException;

    void delete(Long id);

    Optional<User> addUserRoles(User user, Long id);

    Optional<User> blockUser(Long id);

    Optional<User> unlockUser(Long id);

}
