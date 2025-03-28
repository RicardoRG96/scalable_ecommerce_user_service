package com.ricardo.scalable.ecommerce.platform.userService.controllers;

import java.util.List;
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

import com.ricardo.scalable.ecommerce.platform.userService.entities.Wishlist;
import com.ricardo.scalable.ecommerce.platform.userService.repositories.dto.WishlistCreationDto;
import com.ricardo.scalable.ecommerce.platform.userService.services.WishlistService;
import static com.ricardo.scalable.ecommerce.platform.libs_common.validation.RequestBodyValidation.*;

import jakarta.validation.Valid;

@RestController
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    @GetMapping("/wishlist/{id}")
    public ResponseEntity<Wishlist> getById(@PathVariable Long id) {
        Optional<Wishlist> wishlist = wishlistService.findById(id);
        boolean isPresent = wishlist.isPresent();
        
        if (isPresent) {
            return ResponseEntity.ok(wishlist.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/wishlist/user/{userId}")
    public ResponseEntity<List<Wishlist>> getByUserId(@PathVariable Long userId) {
        Optional<List<Wishlist>> wishlist = wishlistService.findByUserId(userId);
        boolean isPresent = wishlist.isPresent();
        boolean isEmpty = wishlist.orElseThrow().isEmpty();
        
        if (isPresent && !isEmpty) {
            return ResponseEntity.ok(wishlist.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/wishlist/product-sku/{productSkuId}")
    public ResponseEntity<List<Wishlist>> getByProductSkuId(@PathVariable Long productSkuId) {
        Optional<List<Wishlist>> wishlist = wishlistService.findByProductSkuId(productSkuId);
        boolean isPresent = wishlist.isPresent();
        boolean isEmpty = wishlist.orElseThrow().isEmpty();
        
        if (isPresent && !isEmpty) {
            return ResponseEntity.ok(wishlist.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/wishlist")
    public ResponseEntity<List<Wishlist>> getAll() {
        List<Wishlist> wishlist = wishlistService.findAll();
        
        return ResponseEntity.ok(wishlist);
    }

    @PostMapping("/wishlist")
    public ResponseEntity<?> createWishlist(
        @Valid @RequestBody WishlistCreationDto wishlist,
        BindingResult result
    ) {
        if (result.hasErrors()) {
            return validation(result);
        }
        Optional<Wishlist> newWishlist = wishlistService.save(wishlist);
        boolean isPresent = newWishlist.isPresent();
        
        if (isPresent) {
            return ResponseEntity.status(HttpStatus.CREATED).body(newWishlist.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/wishlist/{id}")
    public ResponseEntity<?> updateWishlist(
        @Valid @RequestBody WishlistCreationDto wishlist,
        @PathVariable Long id,
        BindingResult result
    ) {
        if (result.hasErrors()) {
            return validation(result);
        }
        Optional<Wishlist> updatedWishlist = wishlistService.update(wishlist, id);
        boolean isPresent = updatedWishlist.isPresent();
        
        if (isPresent) {
            return ResponseEntity.ok(updatedWishlist.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/wishlist/{id}")
    public ResponseEntity<?> deleteWishlist(@PathVariable Long id) {
        Optional<Wishlist> wishlist = wishlistService.findById(id);
        boolean isPresent = wishlist.isPresent();

        if (isPresent) {
            wishlistService.delete(id);
            return ResponseEntity.noContent().build();
        }
        
        return ResponseEntity.notFound().build();
    }

}
