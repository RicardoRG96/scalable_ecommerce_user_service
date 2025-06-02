package com.ricardo.scalable.ecommerce.platform.userService.services.testData;

import java.util.List;
import java.util.Optional;

import com.ricardo.scalable.ecommerce.platform.libs_common.entities.ProductSku;
import com.ricardo.scalable.ecommerce.platform.libs_common.entities.User;
import com.ricardo.scalable.ecommerce.platform.userService.model.dto.WishlistCreationDto;
import com.ricardo.scalable.ecommerce.platform.userService.model.entities.Wishlist;

import static com.ricardo.scalable.ecommerce.platform.userService.services.testData.UserServiceTestData.*;
import static com.ricardo.scalable.ecommerce.platform.userService.services.testData.productSku.ProductSkuTestData.*;

public class WishlistServiceTestData {

    public static List<Wishlist> createListOfWishlist() {
        Wishlist wishlist1 = createWishlist001().orElseThrow();
        Wishlist wishlist2 = createWishlist002().orElseThrow();
        Wishlist wishlist3 = createWishlist003().orElseThrow();
        Wishlist wishlist4 = createWishlist004().orElseThrow();
        Wishlist wishlist5 = createWishlist005().orElseThrow();
        Wishlist wishlist6 = createWishlist006().orElseThrow();
        Wishlist wishlist7 = createWishlist007().orElseThrow();

        return List.of(
            wishlist1,
            wishlist2,
            wishlist3, 
            wishlist4, 
            wishlist5, 
            wishlist6, 
            wishlist7
        );
    }

    public static Optional<Wishlist> createWishlist001() {
        User user = createUser001().orElseThrow();
        ProductSku productSku = createProductSku001().orElseThrow();
        Wishlist wishlist = new Wishlist();
        wishlist.setId(1L);
        wishlist.setUser(user);
        wishlist.setProductSku(productSku);
    
        return Optional.of(wishlist);
    }


    public static Optional<Wishlist> createWishlist002() {
        User user = createUser001().orElseThrow();
        ProductSku productSku = createProductSku002().orElseThrow();
        Wishlist wishlist = new Wishlist();
        wishlist.setId(2L);
        wishlist.setUser(user);
        wishlist.setProductSku(productSku);

        return Optional.of(wishlist);
    }

    public static Optional<Wishlist> createWishlist003() {
        User user = createUser001().orElseThrow();
        ProductSku productSku = createProductSku003().orElseThrow();
        Wishlist wishlist = new Wishlist();
        wishlist.setId(3L);
        wishlist.setUser(user);
        wishlist.setProductSku(productSku);

        return Optional.of(wishlist);
    }

    public static Optional<Wishlist> createWishlist004() {
        User user = createUser002().orElseThrow();
        ProductSku productSku = createProductSku001().orElseThrow();
        Wishlist wishlist = new Wishlist();
        wishlist.setId(4L);
        wishlist.setUser(user);
        wishlist.setProductSku(productSku);

        return Optional.of(wishlist);
    }

    public static Optional<Wishlist> createWishlist005() {
        User user = createUser002().orElseThrow();
        ProductSku productSku = createProductSku004().orElseThrow();
        Wishlist wishlist = new Wishlist();
        wishlist.setId(5L);
        wishlist.setUser(user);
        wishlist.setProductSku(productSku);

        return Optional.of(wishlist);
    }

    public static Optional<Wishlist> createWishlist006() {
        User user = createUser002().orElseThrow();
        ProductSku productSku = createProductSku005().orElseThrow();
        Wishlist wishlist = new Wishlist();
        wishlist.setId(6L);
        wishlist.setUser(user);
        wishlist.setProductSku(productSku);
        
        return Optional.of(wishlist);
    }

    public static Optional<Wishlist> createWishlist007() {
        User user = createUser002().orElseThrow();
        ProductSku productSku = createProductSku007().orElseThrow();
        Wishlist wishlist = new Wishlist();
        wishlist.setId(7L);
        wishlist.setUser(user);
        wishlist.setProductSku(productSku);

        return Optional.of(wishlist);
    }

    public static Optional<List<Wishlist>> createListOfWishlistByUserId1() {
        Wishlist wishlist1 = createWishlist001().orElseThrow();
        Wishlist wishlist2 = createWishlist002().orElseThrow();
        Wishlist wishlist3 = createWishlist003().orElseThrow();

        return Optional.of(
            List.of(
                wishlist1,
                wishlist2,
                wishlist3
            )
        );
    }

    public static Optional<List<Wishlist>> createListOfWishlistByUserId2() {
        Wishlist wishlist4 = createWishlist004().orElseThrow();
        Wishlist wishlist5 = createWishlist005().orElseThrow();
        Wishlist wishlist6 = createWishlist006().orElseThrow();
        Wishlist wishlist7 = createWishlist007().orElseThrow();

        return Optional.of(
            List.of(
                wishlist4,
                wishlist5,
                wishlist6,
                wishlist7
            )
        );
    }

    public static Optional<List<Wishlist>> createListOfWishlistByProductSkuId1() {
        Wishlist wishlist1 = createWishlist001().orElseThrow();
        Wishlist wishlist4 = createWishlist004().orElseThrow();

        return Optional.of(
            List.of(
                wishlist1,
                wishlist4
            )
        );
    }

    public static Optional<List<Wishlist>> createListOfWishlistByProductSkuId() {
        Wishlist wishlist1 = createWishlist001().orElseThrow();
        Wishlist wishlist4 = createWishlist004().orElseThrow();

        return Optional.of(
            List.of(
                wishlist1,
                wishlist4
            )
        );
    }
    
    public static WishlistCreationDto createWishlistCreationDto() {
        WishlistCreationDto wishlist = new WishlistCreationDto();
        wishlist.setUserId(1L);
        wishlist.setProductSkuId(6L);

        return wishlist;
    }

    public static Wishlist createWishlistCreationResponse() {
        User user = createUser001().orElseThrow();
        ProductSku productSku = createProductSku006().orElseThrow();
        Wishlist wishlist = new Wishlist();
        wishlist.setId(8L);
        wishlist.setUser(user);
        wishlist.setProductSku(productSku);

        return wishlist;
    }

    public static Wishlist createUpdatedWishlist() {
        User user = createUser002().orElseThrow();
        ProductSku productSku = createProductSku006().orElseThrow();
        Wishlist wishlist = createWishlist006().orElseThrow();

        wishlist.setUser(user);
        wishlist.setProductSku(productSku);

        return wishlist;
    }
}
