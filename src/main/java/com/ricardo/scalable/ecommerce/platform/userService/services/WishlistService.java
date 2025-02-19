package com.ricardo.scalable.ecommerce.platform.userService.services;

import java.util.List;
import java.util.Optional;

import com.ricardo.scalable.ecommerce.platform.userService.entities.Wishlist;

public interface WishlistService {

    Optional<Wishlist> findById(Long id);

    Optional<List<Wishlist>> findByUserId(Long userId);

    Optional<List<Wishlist>> findByProductSkuId(Long productSkuId);

    Iterable<Wishlist> findAll();

    Optional<Wishlist> save(Wishlist wishlist);

    Optional<Wishlist> update(Wishlist wishlist);

    void delete(Long id);

}
