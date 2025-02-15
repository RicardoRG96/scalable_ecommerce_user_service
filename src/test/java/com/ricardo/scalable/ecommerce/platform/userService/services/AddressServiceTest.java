package com.ricardo.scalable.ecommerce.platform.userService.services;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.ricardo.scalable.ecommerce.platform.userService.entities.Address;
import com.ricardo.scalable.ecommerce.platform.userService.repositories.AddressRepository;
import com.ricardo.scalable.ecommerce.platform.userService.repositories.UserRepository;

import static com.ricardo.scalable.ecommerce.platform.userService.services.testData.AddressServiceTestData.*;

@SpringBootTest
public class AddressServiceTest {

    @MockitoBean
    private AddressRepository addressRepository;

    @MockitoBean
    private UserRepository userRepository;

    @Autowired
    private AddressService addressService;

    @Test
    void testFindById() {
        when(addressRepository.findById(1L)).thenReturn(createAddress001());

        Optional<Address> address = addressService.findById(1L);

        assertAll(
            () -> assertTrue(address.isPresent()),
            () -> assertEquals("Casa en Viña del Mar", address.get().getTitle()),
            () -> assertEquals("Avenida San Martín 456", address.get().getAddressLine1()),
            () -> assertEquals("Departamento 3C", address.get().getAddressLine2()),
            () -> assertEquals("Chile", address.get().getCountry()),
            () -> assertEquals("Viña del Mar", address.get().getCity()),
            () -> assertEquals("Viña del Mar", address.get().getCommune()),
            () -> assertEquals("2520000", address.get().getPostalCode()),
            () -> assertEquals("Cerca del Reloj de Flores", address.get().getLandmark())
        );
    }

}
