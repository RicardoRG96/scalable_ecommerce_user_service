package com.ricardo.scalable.ecommerce.platform.userService.repositories;

import com.ricardo.scalable.ecommerce.platform.libs_common.entities.User;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

}
