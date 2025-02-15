package com.ricardo.scalable.ecommerce.platform.userService.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.ricardo.scalable.ecommerce.platform.userService.entities.Address;

public interface AddressRepository extends CrudRepository<Address, Long> {

    Optional<List<Address>> findByUserId(Long userId);

    Optional<List<Address>> findByUserIdAndTitle(Long userId, String title);

    Optional<List<Address>> findByUserIdAndAddressLine1(Long userId, String addressLine1);

    Optional<List<Address>> findByUserIdAndCountry(Long userId, String country);

    Optional<List<Address>> findByUserIdAndCity(Long userId, String city);

    Optional<List<Address>> findByUserIdAndCommune(Long userId, String commune);

    Optional<List<Address>> findByUserIdAndPostalCode(Long userId, String postalCode);

    Optional<List<Address>> findByUserIdAndLandmark(Long userId, String landmark);

}
