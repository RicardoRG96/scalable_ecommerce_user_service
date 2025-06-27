package com.ricardo.scalable.ecommerce.platform.userService.model.repositories;

import com.ricardo.scalable.ecommerce.platform.libs_common.entities.User;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    @Query(value = """
        SELECT * FROM users 
        WHERE MONTH(birth_date) = :month 
        AND DAY(birth_date) = :day
        AND enabled = TRUE
    """, nativeQuery = true)
    List<User> findByBirthdayMonthAndDay(@Param("month") int month, @Param("day") int day);

    Optional<User> findByVerificationToken(String verificationToken);

}
