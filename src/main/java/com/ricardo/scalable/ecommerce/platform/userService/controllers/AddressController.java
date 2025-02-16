package com.ricardo.scalable.ecommerce.platform.userService.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ricardo.scalable.ecommerce.platform.userService.entities.Address;
import com.ricardo.scalable.ecommerce.platform.userService.repositories.dto.AddressCreationDto;
import com.ricardo.scalable.ecommerce.platform.userService.services.AddressService;

import jakarta.validation.Valid;

@RestController
public class AddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping("/addresses/{id}")
    public ResponseEntity<Address> getAddressById(@PathVariable Long id) {
        Optional<Address> address = addressService.findById(id);
        boolean isPresent = address.isPresent();

        if (isPresent) {
            return ResponseEntity.ok(address.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/addresses/user/{userId}")
    public ResponseEntity<List<Address>> getAddressByUserId(@PathVariable Long userId) {
        Optional<List<Address>> address = addressService.findByUserId(userId);
        boolean isPresent = address.isPresent();
        boolean isEmpty = address.isEmpty();

        if (isPresent && !isEmpty) {
            return ResponseEntity.ok(address.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/addresses/user/{userId}/title/{title}")
    public ResponseEntity<List<Address>> getAddressByUserIdAndTitle(@PathVariable Long userId, @PathVariable String title) {
        Optional<List<Address>> address = addressService.findByUserIdAndTitle(userId, title);
        boolean isPresent = address.isPresent();
        boolean isEmpty = address.isEmpty();

        if (isPresent && !isEmpty) {
            return ResponseEntity.ok(address.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/addresses/user/{userId}/addressLine1/{addressLine1}")
    public ResponseEntity<List<Address>> getAddressByUserIdAndAddressLine1(@PathVariable Long userId, @PathVariable String addressLine1) {
        Optional<List<Address>> address = addressService.findByUserIdAndAddressLine1(userId, addressLine1);
        boolean isPresent = address.isPresent();
        boolean isEmpty = address.isEmpty();

        if (isPresent && !isEmpty) {
            return ResponseEntity.ok(address.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/addresses/user/{userId}/country/{country}")
    public ResponseEntity<List<Address>> getAddressByUserIdAndCountry(@PathVariable Long userId, @PathVariable String country) {
        Optional<List<Address>> address = addressService.findByUserIdAndCountry(userId, country);
        boolean isPresent = address.isPresent();
        boolean isEmpty = address.isEmpty();

        if (isPresent && !isEmpty) {
            return ResponseEntity.ok(address.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/addresses/user/{userId}/city/{city}")
    public ResponseEntity<List<Address>> getAddressByUserIdAndCity(@PathVariable Long userId, @PathVariable String city) {
        Optional<List<Address>> address = addressService.findByUserIdAndCity(userId, city);
        boolean isPresent = address.isPresent();
        boolean isEmpty = address.isEmpty();

        if (isPresent && !isEmpty) {
            return ResponseEntity.ok(address.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/addresses/user/{userId}/commune/{commune}")
    public ResponseEntity<List<Address>> getAddressByUserIdAndCommune(@PathVariable Long userId, @PathVariable String commune) {
        Optional<List<Address>> address = addressService.findByUserIdAndCommune(userId, commune);
        boolean isPresent = address.isPresent();
        boolean isEmpty = address.isEmpty();

        if (isPresent && !isEmpty) {
            return ResponseEntity.ok(address.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/addresses/user/{userId}/postalCode/{postalCode}")
    public ResponseEntity<List<Address>> getAddressByUserIdAndPostalCode(@PathVariable Long userId, @PathVariable String postalCode) {
        Optional<List<Address>> address = addressService.findByUserIdAndPostalCode(userId, postalCode);
        boolean isPresent = address.isPresent();
        boolean isEmpty = address.isEmpty();

        if (isPresent && !isEmpty) {
            return ResponseEntity.ok(address.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/addresses/user/{userId}/landmark/{landmark}")
    public ResponseEntity<List<Address>> getAddressByUserIdAndLandmark(@PathVariable Long userId, @PathVariable String landmark) {
        Optional<List<Address>> address = addressService.findByUserIdAndLandmark(userId, landmark);
        boolean isPresent = address.isPresent();
        boolean isEmpty = address.isEmpty();

        if (isPresent && !isEmpty) {
            return ResponseEntity.ok(address.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/addresses")
    public ResponseEntity<Iterable<Address>> getAllAddresses() {
        Iterable<Address> addresses = addressService.findAll();
        return ResponseEntity.ok(addresses);
    }

    @PostMapping("/addresses")
    public ResponseEntity<?> createAddress(
        @Valid @RequestBody AddressCreationDto address,
        BindingResult result
    ) {
        if (result.hasErrors()) {
            return this.validation(result);
        }
        Optional<Address> newAddress = addressService.save(address);
        boolean isPresent = newAddress.isPresent();

        if (isPresent) {
            return ResponseEntity.status(HttpStatus.CREATED).body(newAddress.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();

        result.getFieldErrors()
                .forEach(err -> errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage()));

        return ResponseEntity.badRequest().body(errors);
    }

    @PutMapping("/addresses/{id}")
    public ResponseEntity<?> updateAddress(
        @Valid @RequestBody Address address,
        @PathVariable Long id,
        BindingResult result
    ) {
        if (result.hasErrors()) {
            return this.validation(result);
        }
        Optional<Address> updatedAddress = addressService.update(address, id);
        boolean isPresent = updatedAddress.isPresent();

        if (isPresent) {
            return ResponseEntity.ok(updatedAddress.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/addresses/{id}")
    public ResponseEntity<?> deleteAddress(@PathVariable Long id) {
        Optional<Address> addressOptional = addressService.findById(id);

        if (addressOptional.isPresent()) {
            addressService.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
