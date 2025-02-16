package com.ricardo.scalable.ecommerce.platform.userService.services;

import static org.mockito.Mockito.when;

import java.util.List;
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
import com.ricardo.scalable.ecommerce.platform.userService.repositories.dto.AddressCreationDto;

import static com.ricardo.scalable.ecommerce.platform.userService.services.testData.AddressServiceTestData.*;
import static com.ricardo.scalable.ecommerce.platform.userService.services.testData.UserServiceTestData.*;

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

    @Test
    void testFindByUserId() {
        when(addressRepository.findByUserId(1L)).thenReturn(createListOfAddressWithUserId1());

        Optional<List<Address>> addresses = addressService.findByUserId(1L);

        assertAll(
            () -> assertTrue(addresses.isPresent()),
            () -> assertEquals(2, addresses.orElseThrow().size()),
            () -> assertEquals(1L, addresses.orElseThrow().get(0).getUser().getId()),
            () -> assertEquals(1L, addresses.orElseThrow().get(1).getUser().getId()),
            () -> assertEquals("Casa en Viña del Mar", addresses.orElseThrow().get(0).getTitle()),
            () -> assertEquals("Casa en Valparaíso", addresses.orElseThrow().get(1).getTitle()),
            () -> assertEquals("Avenida San Martín 456", addresses.orElseThrow().get(0).getAddressLine1()),
            () -> assertEquals("Subida Ecuador 101", addresses.orElseThrow().get(1).getAddressLine1())
        );
    }

    @Test
    void testFindByUserIdAndTitle() {
        Optional<List<Address>> addressesResult = Optional.of(List.of(createAddress001().orElseThrow()));

        when(addressRepository.findByUserIdAndTitle(1L, "Casa en Viña del Mar")).thenReturn(addressesResult);
        
        Optional<List<Address>> addresses = addressService.findByUserIdAndTitle(1L, "Casa en Viña del Mar");

        assertAll(
            () -> assertTrue(addresses.isPresent()),
            () -> assertEquals(1, addresses.orElseThrow().size()),
            () -> assertEquals("Casa en Viña del Mar", addresses.orElseThrow().get(0).getTitle()),
            () -> assertEquals(1L, addresses.orElseThrow().get(0).getUser().getId()),
            () -> assertEquals("Avenida San Martín 456", addresses.orElseThrow().get(0).getAddressLine1())
        );
    }

    @Test
    void testFindByUserIdAndAddressLine1() {
        Optional<List<Address>> addressesResult = Optional.of(List.of(createAddress002().orElseThrow()));

        when(addressRepository.findByUserIdAndAddressLine1(2L, "Calle Barros Arana 789")).thenReturn(addressesResult);
        
        Optional<List<Address>> addresses = addressService.findByUserIdAndAddressLine1(2L, "Calle Barros Arana 789");

        assertAll(
            () -> assertTrue(addresses.isPresent()),
            () -> assertEquals(1, addresses.orElseThrow().size()),
            () -> assertEquals("Oficina en Concepción", addresses.orElseThrow().get(0).getTitle()),
            () -> assertEquals(2L, addresses.orElseThrow().get(0).getUser().getId()),
            () -> assertEquals("Calle Barros Arana 789", addresses.orElseThrow().get(0).getAddressLine1())
        );
    }

    @Test
    void testFindByUserIdAndCountry() {
    Optional<List<Address>> addressesResult = createListOfAddressWithUserId1AndCountry();

        when(addressRepository.findByUserIdAndCountry(1L, "Chile")).thenReturn(addressesResult);
        
        Optional<List<Address>> addresses = addressService.findByUserIdAndCountry(1L, "Chile");

        assertAll(
            () -> assertTrue(addresses.isPresent()),
            () -> assertEquals(2, addresses.orElseThrow().size()),
            () -> assertEquals("Casa en Viña del Mar", addresses.orElseThrow().get(0).getTitle()),
            () -> assertEquals("Casa en Valparaíso", addresses.orElseThrow().get(1).getTitle()),
            () -> assertEquals(1L, addresses.orElseThrow().get(0).getUser().getId()),
            () -> assertEquals(1L, addresses.orElseThrow().get(1).getUser().getId()),
            () -> assertEquals("Chile", addresses.orElseThrow().get(0).getCountry()),
            () -> assertEquals("Chile", addresses.orElseThrow().get(1).getCountry()),
            () -> assertEquals("Avenida San Martín 456", addresses.orElseThrow().get(0).getAddressLine1()),
            () -> assertEquals("Subida Ecuador 101", addresses.orElseThrow().get(1).getAddressLine1())
        );
    }

    @Test
    void testFindByUserIdAndCity() {
        Optional<List<Address>> addressesResult = Optional.of(List.of(createAddress001().orElseThrow()));

        when(addressRepository.findByUserIdAndCity(1L, "Viña del Mar")).thenReturn(addressesResult);
        
        Optional<List<Address>> addresses = addressService.findByUserIdAndCity(1L, "Viña del Mar");

        assertAll(
            () -> assertTrue(addresses.isPresent()),
            () -> assertEquals(1, addresses.orElseThrow().size()),
            () -> assertEquals("Casa en Viña del Mar", addresses.orElseThrow().get(0).getTitle()),
            () -> assertEquals(1L, addresses.orElseThrow().get(0).getUser().getId()),
            () -> assertEquals("Viña del Mar", addresses.orElseThrow().get(0).getCity()),
            () -> assertEquals("Avenida San Martín 456", addresses.orElseThrow().get(0).getAddressLine1())
        );
    }

    @Test
    void testFindByUserIdAndCommune() {
        Optional<List<Address>> addressesResult = Optional.of(List.of(createAddress001().orElseThrow()));

        when(addressRepository.findByUserIdAndCommune(1L, "Viña del Mar")).thenReturn(addressesResult);
        
        Optional<List<Address>> addresses = addressService.findByUserIdAndCommune(1L, "Viña del Mar");

        assertAll(
            () -> assertTrue(addresses.isPresent()),
            () -> assertEquals(1, addresses.orElseThrow().size()),
            () -> assertEquals("Casa en Viña del Mar", addresses.orElseThrow().get(0).getTitle()),
            () -> assertEquals(1L, addresses.orElseThrow().get(0).getUser().getId()),
            () -> assertEquals("Viña del Mar", addresses.orElseThrow().get(0).getCommune()),
            () -> assertEquals("Avenida San Martín 456", addresses.orElseThrow().get(0).getAddressLine1())
        );
    }

    @Test
    void testFindByUserIdAndPostalCode() {
        Optional<List<Address>> addressesResult = Optional.of(List.of(createAddress001().orElseThrow()));

        when(addressRepository.findByUserIdAndPostalCode(1L, "2520000")).thenReturn(addressesResult);
        
        Optional<List<Address>> addresses = addressService.findByUserIdAndPostalCode(1L, "2520000");

        assertAll(
            () -> assertTrue(addresses.isPresent()),
            () -> assertEquals(1, addresses.orElseThrow().size()),
            () -> assertEquals("Casa en Viña del Mar", addresses.orElseThrow().get(0).getTitle()),
            () -> assertEquals(1L, addresses.orElseThrow().get(0).getUser().getId()),
            () -> assertEquals("2520000", addresses.orElseThrow().get(0).getPostalCode()),
            () -> assertEquals("Avenida San Martín 456", addresses.orElseThrow().get(0).getAddressLine1())
        );
    }

    @Test
    void testFindByUserIdAndLandmark() {
        Optional<List<Address>> addressesResult = Optional.of(List.of(createAddress001().orElseThrow()));

        when(addressRepository.findByUserIdAndLandmark(1L, "Cerca del Reloj de Flores")).thenReturn(addressesResult);
        
        Optional<List<Address>> addresses = addressService.findByUserIdAndLandmark(1L, "Cerca del Reloj de Flores");

        assertAll(
            () -> assertTrue(addresses.isPresent()),
            () -> assertEquals(1, addresses.orElseThrow().size()),
            () -> assertEquals("Casa en Viña del Mar", addresses.orElseThrow().get(0).getTitle()),
            () -> assertEquals(1L, addresses.orElseThrow().get(0).getUser().getId()),
            () -> assertEquals("Cerca del Reloj de Flores", addresses.orElseThrow().get(0).getLandmark()),
            () -> assertEquals("Avenida San Martín 456", addresses.orElseThrow().get(0).getAddressLine1())
        );
    }

    @Test
    void testFindAll() {
        when(addressRepository.findAll()).thenReturn(createListOfAddresses());

        List<Address> addresses = (List<Address>) addressService.findAll();

        assertAll(
            () -> assertNotNull(addresses),
            () -> assertEquals(4, addresses.size()),
            () -> assertEquals("Casa en Viña del Mar", addresses.get(0).getTitle()),
            () -> assertEquals("Oficina en Concepción", addresses.get(1).getTitle()),
            () -> assertEquals("Casa en Valparaíso", addresses.get(2).getTitle()),
            () -> assertEquals("Departamento en La Serena", addresses.get(3).getTitle()),
            () -> assertEquals("Avenida San Martín 456", addresses.get(0).getAddressLine1()),
            () -> assertEquals("Calle Barros Arana 789", addresses.get(1).getAddressLine1()),
            () -> assertEquals("Subida Ecuador 101", addresses.get(2).getAddressLine1()),
            () -> assertEquals("Avenida del Mar 222", addresses.get(3).getAddressLine1())
        );
    }

    @Test
    void testSave() {
        AddressCreationDto addressCreationRequest = createAddressCreationDto();
        Address addressResult = createAddressCreationResponse();

        when(userRepository.findById(2L)).thenReturn(createUser002());
        when(addressRepository.save(any())).thenReturn(addressResult);

        Optional<Address> addressSaved = addressService.save(addressCreationRequest);

        assertAll(
            () -> assertTrue(addressSaved.isPresent()),
            () -> assertEquals(2L, addressSaved.orElseThrow().getUser().getId()),
            () -> assertEquals("Casa en Santiago", addressSaved.orElseThrow().getTitle()),
            () -> assertEquals("Avenida Providencia 123", addressSaved.orElseThrow().getAddressLine1()),
            () -> assertEquals("Departamento 5B", addressSaved.orElseThrow().getAddressLine2()),
            () -> assertEquals("Chile", addressSaved.orElseThrow().getCountry()),
            () -> assertEquals("Santiago", addressSaved.orElseThrow().getCity()),
            () -> assertEquals("Providencia", addressSaved.orElseThrow().getCommune()),
            () -> assertEquals("7500000", addressSaved.orElseThrow().getPostalCode()),
            () -> assertEquals("Cerca del Costanera Center", addressSaved.orElseThrow().getLandmark())
        );
    }

    @Test
    void testUpdate() {
        Address updatedAddress = createUpdatedAddress();

        when(addressRepository.findById(4L)).thenReturn(createAddress004());
        when(addressRepository.save(any())).thenReturn(updatedAddress);

        Optional<Address> addressUpdated = addressService.update(updatedAddress, 4L);

        assertAll(
            () -> assertTrue(addressUpdated.isPresent()),
            () -> assertEquals("Depto en La Serena", addressUpdated.orElseThrow().getTitle()),
            () -> assertEquals("Avenida del Mar 222", addressUpdated.orElseThrow().getAddressLine1()),
            () -> assertEquals("Departamento 12A", addressUpdated.orElseThrow().getAddressLine2()),
            () -> assertEquals("Chile", addressUpdated.orElseThrow().getCountry()),
            () -> assertEquals("La Serena", addressUpdated.orElseThrow().getCity()),
            () -> assertEquals("La Serena", addressUpdated.orElseThrow().getCommune()),
            () -> assertEquals("1720000", addressUpdated.orElseThrow().getPostalCode()),
            () -> assertEquals("Frente a la playa del faro", addressUpdated.orElseThrow().getLandmark())
        );
    }

}
