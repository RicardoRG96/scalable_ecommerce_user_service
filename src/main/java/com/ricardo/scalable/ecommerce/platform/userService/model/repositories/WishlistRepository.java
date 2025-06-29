package com.ricardo.scalable.ecommerce.platform.userService.model.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.ricardo.scalable.ecommerce.platform.userService.model.entities.Wishlist;

public interface WishlistRepository extends CrudRepository<Wishlist, Long> {

    Optional<List<Wishlist>> findByUserId(Long userId);

    Optional<List<Wishlist>> findByProductSkuId(Long productSkuId);

}
