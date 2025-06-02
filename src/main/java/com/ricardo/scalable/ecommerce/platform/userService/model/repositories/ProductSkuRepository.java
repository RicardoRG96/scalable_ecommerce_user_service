package com.ricardo.scalable.ecommerce.platform.userService.model.repositories;

import org.springframework.data.repository.CrudRepository;

import com.ricardo.scalable.ecommerce.platform.libs_common.entities.ProductSku;

public interface ProductSkuRepository extends CrudRepository<ProductSku, Long> {

}
