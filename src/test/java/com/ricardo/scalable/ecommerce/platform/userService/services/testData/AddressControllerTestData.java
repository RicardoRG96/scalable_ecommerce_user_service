package com.ricardo.scalable.ecommerce.platform.userService.services.testData;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;

import com.ricardo.scalable.ecommerce.platform.libs_common.entities.Address;
import com.ricardo.scalable.ecommerce.platform.libs_common.entities.User;
import com.ricardo.scalable.ecommerce.platform.userService.repositories.dto.AddressCreationDto;

public class AddressControllerTestData {

    public static AddressCreationDto createAddressCreationDto() {
        AddressCreationDto addressCreationDto = new AddressCreationDto();

        addressCreationDto.setUserId(1L);
        addressCreationDto.setTitle("Casa en Viña del Mar");
        addressCreationDto.setAddressLine1("Avenida San Martín 456");
        addressCreationDto.setAddressLine2("Departamento 3C");
        addressCreationDto.setCountry("Chile");
        addressCreationDto.setCity("Viña del Mar");
        addressCreationDto.setCommune("Viña del Mar");
        addressCreationDto.setPostalCode("2520000");
        addressCreationDto.setLandmark("Cerca del Reloj de Flores");

        return addressCreationDto;
    }

    public static Address createAddress() {
        Address address = new Address();
        User user = createUser();

        address.setId(7L);
        address.setUser(user);
        address.setTitle("Casa en Viña del Mar");
        address.setAddressLine1("Avenida San Martín 456");
        address.setAddressLine2("Departamento 3C");
        address.setCountry("Chile");
        address.setCity("Viña del Mar");
        address.setCommune("Viña del Mar");
        address.setPostalCode("2520000");
        address.setLandmark("Cerca del Reloj de Flores");
        address.setCreatedAt(Timestamp.from(Instant.now()));
        address.setUpdatedAt(Timestamp.from(Instant.now()));

        return address;
    }

    private static User createUser() {
        User user = new User();

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

        return user;
    }

}
