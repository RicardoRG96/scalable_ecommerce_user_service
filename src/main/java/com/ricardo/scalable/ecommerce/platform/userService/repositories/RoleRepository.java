package com.ricardo.scalable.ecommerce.platform.userService.repositories;

import com.ricardo.scalable.ecommerce.platform.userService.entities.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Long> {

    Optional<Role> findByName(String name);

}
