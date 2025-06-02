package com.ricardo.scalable.ecommerce.platform.userService.services;

import java.util.List;
import java.util.Optional;

import com.ricardo.scalable.ecommerce.platform.libs_common.entities.Address;
import com.ricardo.scalable.ecommerce.platform.userService.model.dto.AddressCreationDto;

public interface AddressService {

    Optional<Address> findById(Long id);

    Optional<List<Address>> findByUserId(Long userId);

    Optional<List<Address>> findByUserIdAndTitle(Long userId, String title);

    Optional<List<Address>> findByUserIdAndAddressLine1(Long userId, String addressLine1);

    Optional<List<Address>> findByUserIdAndCountry(Long userId, String country);

    Optional<List<Address>> findByUserIdAndCity(Long userId, String city);

    Optional<List<Address>> findByUserIdAndCommune(Long userId, String commune);

    Optional<List<Address>> findByUserIdAndPostalCode(Long userId, String postalCode);

    Optional<List<Address>> findByUserIdAndLandmark(Long userId, String landmark);

    Iterable<Address> findAll();

    Optional<Address> save(AddressCreationDto address);

    Optional<Address> update(Address address, Long id);

    void delete(Long id);

}
