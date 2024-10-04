package com.mobile_shop.service;

import com.mobile_shop.dto.ProductDTO;
import com.mobile_shop.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> getAll();


    Optional<Product> getOne(Integer id);

    void deleteByPromotionId(Integer id);

    Product updateProduct(Integer id, String name, String detail, String image, Double price, Integer quantity, String category);

    Product parseProductDtoToProduct(ProductDTO productDTO);
}
