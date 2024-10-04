package com.mobile_shop.service;

import com.mobile_shop.dto.ProductDTO;
import com.mobile_shop.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAll();
}
