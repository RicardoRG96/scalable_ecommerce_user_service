package com.ricardo.scalable.ecommerce.platform.userService.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ricardo.scalable.ecommerce.platform.libs_common.entities.Address;
import com.ricardo.scalable.ecommerce.platform.libs_common.entities.User;
import com.ricardo.scalable.ecommerce.platform.userService.model.dto.AddressCreationDto;
import com.ricardo.scalable.ecommerce.platform.userService.model.repositories.AddressRepository;
import com.ricardo.scalable.ecommerce.platform.userService.model.repositories.UserRepository;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<Address> findById(Long id) {
        return addressRepository.findById(id);
    }

    @Override
    public Optional<List<Address>> findByUserId(Long userId) {
        return addressRepository.findByUserId(userId);
    }

    @Override
    public Optional<List<Address>> findByUserIdAndTitle(Long userId, String title) {
        return addressRepository.findByUserIdAndTitle(userId, title);
    }

    @Override
    public Optional<List<Address>> findByUserIdAndAddressLine1(Long userId, String addressLine1) {
        return addressRepository.findByUserIdAndAddressLine1(userId, addressLine1);
    }

    @Override
    public Optional<List<Address>> findByUserIdAndCountry(Long userId, String country) {
        return addressRepository.findByUserIdAndCountry(userId, country);
    }

    @Override
    public Optional<List<Address>> findByUserIdAndCity(Long userId, String city) {
        return addressRepository.findByUserIdAndCity(userId, city);
    }

    @Override
    public Optional<List<Address>> findByUserIdAndCommune(Long userId, String commune) {
        return addressRepository.findByUserIdAndCommune(userId, commune);
    }

    @Override
    public Optional<List<Address>> findByUserIdAndPostalCode(Long userId, String postalCode) {
        return addressRepository.findByUserIdAndPostalCode(userId, postalCode);
    }

    @Override
    public Optional<List<Address>> findByUserIdAndLandmark(Long userId, String landmark) {
        return addressRepository.findByUserIdAndLandmark(userId, landmark);
    }

    @Override
    public Iterable<Address> findAll() {
        return addressRepository.findAll();
    }

    @Override
    public Optional<Address> save(AddressCreationDto address) {
        Long userId = address.getUserId();
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            User user = userOptional.orElseThrow();
            Address newAddress = new Address();

            newAddress.setUser(user);
            newAddress.setTitle(address.getTitle());
            newAddress.setAddressLine1(address.getAddressLine1());
            newAddress.setAddressLine2(address.getAddressLine2());
            newAddress.setCountry(address.getCountry());
            newAddress.setCity(address.getCity());
            newAddress.setCommune(address.getCommune());
            newAddress.setPostalCode(address.getPostalCode());
            newAddress.setLandmark(address.getLandmark());

            return Optional.of(addressRepository.save(newAddress));
        }
        return Optional.empty();
    }

    @Override
    public Optional<Address> update(Address address, Long id) {
        Optional<Address> dbAddress = addressRepository.findById(id);
        
        return dbAddress.map(addr -> {
            addr.setTitle(address.getTitle());
            addr.setAddressLine1(address.getAddressLine1());
            addr.setAddressLine2(address.getAddressLine2());
            addr.setCountry(address.getCountry());
            addr.setCity(address.getCity());
            addr.setCommune(address.getCommune());
            addr.setPostalCode(address.getPostalCode());
            addr.setLandmark(address.getLandmark());
            return Optional.of(addressRepository.save(addr));
        }).orElseGet(Optional::empty);
    }

    @Override
    public void delete(Long id) {
        addressRepository.deleteById(id);
    }

}
