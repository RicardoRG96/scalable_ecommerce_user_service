package com.ricardo.scalable.ecommerce.platform.userService.services;

import java.util.List;
import java.util.Optional;

import com.ricardo.scalable.ecommerce.platform.userService.model.dto.WishlistCreationDto;
import com.ricardo.scalable.ecommerce.platform.userService.model.entities.Wishlist;

public interface WishlistService {

    Optional<Wishlist> findById(Long id);

    Optional<List<Wishlist>> findByUserId(Long userId);

    Optional<List<Wishlist>> findByProductSkuId(Long productSkuId);

    List<Wishlist> findAll();

    Optional<Wishlist> save(WishlistCreationDto wishlist);

    Optional<Wishlist> update(WishlistCreationDto wishlist, Long id);

    void delete(Long id);

}
