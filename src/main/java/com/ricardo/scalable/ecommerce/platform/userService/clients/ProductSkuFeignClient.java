package com.ricardo.scalable.ecommerce.platform.userService.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ricardo.scalable.ecommerce.platform.libs_common.entities.ProductSku;

@FeignClient(name = "product-service")
public interface ProductSkuFeignClient {

    @GetMapping("/product-sku/{id}")
    ProductSku getById(@PathVariable Long id);

}
