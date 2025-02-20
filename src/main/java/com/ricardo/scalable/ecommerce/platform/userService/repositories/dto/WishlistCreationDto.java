package com.ricardo.scalable.ecommerce.platform.userService.repositories.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class WishlistCreationDto {

    @Min(1)
    @NotNull
    private Long userId;

    @Min(1)
    @NotNull
    private Long productSkuId;

    public WishlistCreationDto() {
    }

    public WishlistCreationDto(Long userId, Long productSkuId) {
        this.userId = userId;
        this.productSkuId = productSkuId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProductSkuId() {
        return productSkuId;
    }

    public void setProductSkuId(Long productSkuId) {
        this.productSkuId = productSkuId;
    }

}
