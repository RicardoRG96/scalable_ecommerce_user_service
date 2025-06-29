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

import com.ricardo.scalable.ecommerce.platform.userService.model.dto.WishlistCreationDto;
import com.ricardo.scalable.ecommerce.platform.userService.model.entities.Wishlist;
import com.ricardo.scalable.ecommerce.platform.userService.model.repositories.ProductSkuRepository;
import com.ricardo.scalable.ecommerce.platform.userService.model.repositories.UserRepository;
import com.ricardo.scalable.ecommerce.platform.userService.model.repositories.WishlistRepository;

import static com.ricardo.scalable.ecommerce.platform.userService.services.testData.WishlistServiceTestData.*;
import static com.ricardo.scalable.ecommerce.platform.userService.services.testData.UserServiceTestData.*;
import static com.ricardo.scalable.ecommerce.platform.userService.services.testData.productSku.ProductSkuTestData.*;

@SpringBootTest
public class WishlistServiceTest {

    @MockitoBean
    private WishlistRepository wishlistRepository;

    @MockitoBean
    private UserRepository userRepository;

    @MockitoBean
    private ProductSkuRepository productSkuRepository;

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

    @Test
    void testSave() {
        WishlistCreationDto wishlistCreationRequest = createWishlistCreationDto();
        Wishlist wishlistCreationResponse = createWishlistCreationResponse();

        when(userRepository.findById(1L)).thenReturn(createUser001());
        when(productSkuRepository.findById(6L)).thenReturn(createProductSku006());
        when(wishlistRepository.save(any())).thenReturn(wishlistCreationResponse);

        Optional<Wishlist> wishlistOptional = wishlistService.save(wishlistCreationRequest);

        assertAll(
            () -> assertTrue(wishlistOptional.isPresent()),
            () -> assertEquals(8L, wishlistOptional.orElseThrow().getId()),
            () -> assertEquals(1L, wishlistOptional.orElseThrow().getUser().getId()),
            () -> assertEquals(6L, wishlistOptional.orElseThrow().getProductSku().getId())
        );
    }

    @Test
    void testUpdate() {
        WishlistCreationDto wishlistCreationRequest = new WishlistCreationDto(2L, 6L);
        Wishlist updatedWishlist = createUpdatedWishlist();

        when(wishlistRepository.findById(6L)).thenReturn(createWishlist006());
        when(userRepository.findById(2L)).thenReturn(createUser002());
        when(productSkuRepository.findById(6L)).thenReturn(createProductSku006());
        when(wishlistRepository.save(any())).thenReturn(updatedWishlist);

        Optional<Wishlist> wishlistOptional = wishlistService.update(wishlistCreationRequest, 6L);

        assertAll(
            () -> assertTrue(wishlistOptional.isPresent()),
            () -> assertEquals(6L, wishlistOptional.orElseThrow().getId()),
            () -> assertEquals(2L, wishlistOptional.orElseThrow().getUser().getId()),
            () -> assertEquals(6L, wishlistOptional.orElseThrow().getProductSku().getId())
        );
    }

    @Test
    void testDelete() {
        wishlistService.delete(1L);

        verify(wishlistRepository, times(1)).deleteById(1L);
    }

}
