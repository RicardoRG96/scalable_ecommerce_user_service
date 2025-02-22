package com.ricardo.scalable.ecommerce.platform.userService.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import com.ricardo.scalable.ecommerce.platform.userService.clients.ProductSkuFeignClient;
import com.ricardo.scalable.ecommerce.platform.userService.entities.Wishlist;
import com.ricardo.scalable.ecommerce.platform.userService.repositories.UserRepository;
import com.ricardo.scalable.ecommerce.platform.userService.repositories.WishlistRepository;

import static com.ricardo.scalable.ecommerce.platform.userService.services.testData.WishlistServiceTestData.*;

@SpringBootTest
public class WishlistServiceTest {

    @MockitoBean
    private WishlistRepository wishlistRepository;

    @MockitoBean
    private UserRepository userRepository;

    @MockitoBean
    private ProductSkuFeignClient productSkuClient;

    @Autowired
    private WishlistService wishlistService;

    @Test
    void testFindById() {
        when(wishlistRepository.findById(1L)).thenReturn(createWishlist001());

        Optional<Wishlist> wishlistOptional = wishlistService.findById(1L);
        
        assertAll(
            () -> assertTrue(wishlistOptional.isPresent()),
            () -> assertEquals(1L, wishlistOptional.orElseThrow().getId()),
            () -> assertEquals(1L, wishlistOptional.orElseThrow().getUser().getId()),
            () -> assertEquals("Ricardo", wishlistOptional.orElseThrow().getUser().getFirstName()),
            () -> assertEquals("Retamal", wishlistOptional.orElseThrow().getUser().getLastName()),
            () -> assertEquals(1L, wishlistOptional.orElseThrow().getProductSku().getId()),
            () -> assertEquals("Notebook Samsung", wishlistOptional.orElseThrow().getProductSku().getProduct().getName())
        );
    }

    @Test
    void testFindByUserId() {
        when(wishlistRepository.findByUserId(2L)).thenReturn(createListOfWishlistByUserId2());

        Optional<List<Wishlist>> wishlistOptional = wishlistService.findByUserId(2L);
        
        assertAll(
            () -> assertTrue(wishlistOptional.isPresent()),
            () -> assertEquals(4L, wishlistOptional.orElseThrow().get(0).getId()),
            () -> assertEquals(5L, wishlistOptional.orElseThrow().get(1).getId()),
            () -> assertEquals(2L, wishlistOptional.orElseThrow().get(0).getUser().getId()),
            () -> assertEquals(2L, wishlistOptional.orElseThrow().get(1).getUser().getId()),
            () -> assertEquals("Mateo", wishlistOptional.orElseThrow().get(0).getUser().getFirstName()),
            () -> assertEquals("Retamal", wishlistOptional.orElseThrow().get(0).getUser().getLastName()),
            () -> assertEquals("Mateo", wishlistOptional.orElseThrow().get(1).getUser().getFirstName()),
            () -> assertEquals("Retamal", wishlistOptional.orElseThrow().get(1).getUser().getLastName()),
            () -> assertEquals(1L, wishlistOptional.orElseThrow().get(0).getProductSku().getId()),
            () -> assertEquals("Notebook Samsung", wishlistOptional.orElseThrow().get(0).getProductSku().getProduct().getName()),
            () -> assertEquals(4L, wishlistOptional.orElseThrow().get(1).getProductSku().getId()),
            () -> assertEquals("Polera manga corta", wishlistOptional.orElseThrow().get(1).getProductSku().getProduct().getName())
        );
    }

    @Test
    void testFindByProductSkuId() {
        when(wishlistRepository.findByProductSkuId(1L)).thenReturn(createListOfWishlistByProductSkuId1());

        Optional<List<Wishlist>> wishlistOptional = wishlistService.findByProductSkuId(1L);
        
        assertAll(
            () -> assertTrue(wishlistOptional.isPresent()),
            () -> assertEquals(1L, wishlistOptional.orElseThrow().get(0).getId()),
            () -> assertEquals(4L, wishlistOptional.orElseThrow().get(1).getId()),
            () -> assertEquals(1L, wishlistOptional.orElseThrow().get(0).getUser().getId()),
            () -> assertEquals(2L, wishlistOptional.orElseThrow().get(1).getUser().getId()),
            () -> assertEquals("Ricardo", wishlistOptional.orElseThrow().get(0).getUser().getFirstName()),
            () -> assertEquals("Mateo", wishlistOptional.orElseThrow().get(1).getUser().getFirstName()),
            () -> assertEquals("Retamal", wishlistOptional.orElseThrow().get(0).getUser().getLastName()),
            () -> assertEquals("Retamal", wishlistOptional.orElseThrow().get(1).getUser().getLastName()),
            () -> assertEquals(1L, wishlistOptional.orElseThrow().get(0).getProductSku().getId()),
            () -> assertEquals("Notebook Samsung", wishlistOptional.orElseThrow().get(0).getProductSku().getProduct().getName()),
            () -> assertEquals(1L, wishlistOptional.orElseThrow().get(1).getProductSku().getId()),
            () -> assertEquals("Notebook Samsung", wishlistOptional.orElseThrow().get(1).getProductSku().getProduct().getName())
        );
    }

    @Test
    void testFindAll() {
        when(wishlistRepository.findAll()).thenReturn(createListOfWishlist());

        List<Wishlist> wishlistList = wishlistService.findAll();
        
        assertAll(
            () -> assertEquals(7, wishlistList.size()),
            () -> assertEquals(1L, wishlistList.get(0).getId()),
            () -> assertEquals(2L, wishlistList.get(1).getId()),
            () -> assertEquals(3L, wishlistList.get(2).getId()),
            () -> assertEquals(4L, wishlistList.get(3).getId()),
            () -> assertEquals(5L, wishlistList.get(4).getId()),
            () -> assertEquals(1L, wishlistList.get(0).getUser().getId()),
            () -> assertEquals(1L, wishlistList.get(1).getUser().getId()),
            () -> assertEquals(1L, wishlistList.get(0).getProductSku().getId()),
            () -> assertEquals(1L, wishlistList.get(3).getProductSku().getId())
        );
    }

}
