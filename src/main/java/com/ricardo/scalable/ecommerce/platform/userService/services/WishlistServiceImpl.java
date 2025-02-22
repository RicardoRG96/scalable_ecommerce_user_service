package com.ricardo.scalable.ecommerce.platform.userService.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ricardo.scalable.ecommerce.platform.libs_common.entities.ProductSku;
import com.ricardo.scalable.ecommerce.platform.userService.clients.ProductSkuFeignClient;
import com.ricardo.scalable.ecommerce.platform.userService.entities.User;
import com.ricardo.scalable.ecommerce.platform.userService.entities.Wishlist;
import com.ricardo.scalable.ecommerce.platform.userService.repositories.ProductSkuRepository;
import com.ricardo.scalable.ecommerce.platform.userService.repositories.UserRepository;
import com.ricardo.scalable.ecommerce.platform.userService.repositories.WishlistRepository;
import com.ricardo.scalable.ecommerce.platform.userService.repositories.dto.WishlistCreationDto;

import feign.FeignException;

@Service
public class WishlistServiceImpl implements WishlistService {

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private UserRepository userRepository;

    // @Autowired
    // private ProductSkuFeignClient productSkuClient;

    @Autowired
    private ProductSkuRepository productSkuRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<Wishlist> findById(Long id) {
        return wishlistRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<Wishlist>> findByUserId(Long userId) {
        return wishlistRepository.findByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<Wishlist>> findByProductSkuId(Long productSkuId) {
        return wishlistRepository.findByProductSkuId(productSkuId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Wishlist> findAll() {
        return (List<Wishlist>) wishlistRepository.findAll();
    }

    @Override
    @Transactional
    public Optional<Wishlist> save(WishlistCreationDto wishlist) {
        // try {
        //     Long userId = wishlist.getUserId();
        //     Long productSkuId = wishlist.getProductSkuId();
        //     Optional<User> user = userRepository.findById(userId);
        //     ProductSku productSku = productSkuClient.getById(productSkuId);

        //     if (user.isPresent()) {
        //         Wishlist newWishlist = new Wishlist();
        //         newWishlist.setUser(user.orElseThrow());
        //         newWishlist.setProductSku(productSku);

        //         return Optional.of(wishlistRepository.save(newWishlist));
        //     }
        //     return Optional.empty();
        // } catch (FeignException e) {
        //     return Optional.empty();
        // }
        Long userId = wishlist.getUserId();
        Long productSkuId = wishlist.getProductSkuId();
        Optional<User> user = userRepository.findById(userId);
        Optional<ProductSku> productSku = productSkuRepository.findById(productSkuId);

        if (user.isPresent() && productSku.isPresent()) {
            Wishlist newWishlist = new Wishlist();
            newWishlist.setUser(user.orElseThrow());
            newWishlist.setProductSku(productSku.orElseThrow());

            return Optional.of(wishlistRepository.save(newWishlist));
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Wishlist> update(WishlistCreationDto wishlist, Long id) {
        // try {
        //     Optional<Wishlist> dbWishlist = wishlistRepository.findById(id);
        //     Optional<User> user = userRepository.findById(wishlist.getUserId());
        //     ProductSku productSku = productSkuClient.getById(wishlist.getProductSkuId());

        //     if (user.isEmpty()) {
        //         return Optional.empty();
        //     }

        //     return dbWishlist.map(w -> {
        //         w.setUser(user.orElseThrow());
        //         w.setProductSku(productSku);
        //         return Optional.of(wishlistRepository.save(w));
        //     }).orElseGet(Optional::empty);
        // } catch (FeignException e) {
        //     return Optional.empty();
        // }
        Optional<Wishlist> dbWishlist = wishlistRepository.findById(id);
        Optional<User> user = userRepository.findById(wishlist.getUserId());
        Optional<ProductSku> productSku = productSkuRepository.findById(wishlist.getProductSkuId());

        if (user.isEmpty() || productSku.isEmpty()) {
            return Optional.empty();
        }

        return dbWishlist.map(w -> {
            w.setUser(user.orElseThrow());
            w.setProductSku(productSku.orElseThrow());
            return Optional.of(wishlistRepository.save(w));
        }).orElseGet(Optional::empty);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        wishlistRepository.deleteById(id);
    }

}
