package com.ricardo.scalable.ecommerce.platform.userService.services;

import java.util.List;
import java.util.Optional;

import com.ricardo.scalable.ecommerce.platform.userService.entities.Wishlist;
import com.ricardo.scalable.ecommerce.platform.userService.repositories.dto.WishlistCreationDto;

public interface WishlistService {

    Optional<Wishlist> findById(Long id);

    Optional<List<Wishlist>> findByUserId(Long userId);

    Optional<List<Wishlist>> findByProductSkuId(Long productSkuId);

    List<Wishlist> findAll();

    Optional<Wishlist> save(WishlistCreationDto wishlist);

    Optional<Wishlist> update(WishlistCreationDto wishlist, Long id);

    void delete(Long id);

}
