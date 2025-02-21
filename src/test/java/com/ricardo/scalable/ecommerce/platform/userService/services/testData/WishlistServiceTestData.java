package com.ricardo.scalable.ecommerce.platform.userService.services.testData;

import java.util.List;
import java.util.Optional;

import com.ricardo.scalable.ecommerce.platform.libs_common.entities.ProductSku;
import com.ricardo.scalable.ecommerce.platform.userService.entities.User;
import com.ricardo.scalable.ecommerce.platform.userService.entities.Wishlist;

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
        Wishlist wishlist = new Wishlist(user, productSku);
        return Optional.of(wishlist);
    }


    public static Optional<Wishlist> createWishlist002() {
        User user = createUser001().orElseThrow();
        ProductSku productSku = createProductSku002().orElseThrow();
        Wishlist wishlist = new Wishlist(user, productSku);
        return Optional.of(wishlist);
    }

    public static Optional<Wishlist> createWishlist003() {
        User user = createUser001().orElseThrow();
        ProductSku productSku = createProductSku003().orElseThrow();
        Wishlist wishlist = new Wishlist(user, productSku);
        return Optional.of(wishlist);
    }

    public static Optional<Wishlist> createWishlist004() {
        User user = createUser002().orElseThrow();
        ProductSku productSku = createProductSku004().orElseThrow();
        Wishlist wishlist = new Wishlist(user, productSku);
        return Optional.of(wishlist);
    }

    public static Optional<Wishlist> createWishlist005() {
        User user = createUser002().orElseThrow();
        ProductSku productSku = createProductSku005().orElseThrow();
        Wishlist wishlist = new Wishlist(user, productSku);
        return Optional.of(wishlist);
    }

    public static Optional<Wishlist> createWishlist006() {
        User user = createUser002().orElseThrow();
        ProductSku productSku = createProductSku006().orElseThrow();
        Wishlist wishlist = new Wishlist(user, productSku);
        return Optional.of(wishlist);
    }

    public static Optional<Wishlist> createWishlist007() {
        User user = createUser002().orElseThrow();
        ProductSku productSku = createProductSku007().orElseThrow();
        Wishlist wishlist = new Wishlist(user, productSku);
        return Optional.of(wishlist);
    }
    
}
