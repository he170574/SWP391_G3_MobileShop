package com.mobile_shop.service.impl;

import com.mobile_shop.dto.ProductDTO;
import com.mobile_shop.entity.Product;
import com.mobile_shop.repository.ProductRepository;
import com.mobile_shop.service.ProductService;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> getOne(Integer id) {
        return productRepository.findById(id);
    }

    public Product parsePromotionDtoToProduct(ProductDTO productDTO) {
        return Product.builder()
                .productID(productDTO.getProductId())
                .deleted(false)
                .productName(productDTO.getProductName())
                .productDetails(productDTO.getProductDetails())
                .productImage(productDTO.getProductImage())
                .price(productDTO.getPrice())
                .CategoryID(productDTO.getCategoryID())
                .stockQuantity(productDTO.getStockQuantity())
                .build();
    }

    public Product save(Product product) {
        Product savedProduct = productRepository.save(product);
        if (savedProduct != null) {
            executorService.submit(() -> {
                List<Account> lstAccount = accountRepository.findAll();
                for (Account account : lstAccount) {
                    sendPromotion(savedPromotion, account.getEmail());
                }
            });
        }
        return savedPromotion;
    }

    @Override
    public void deleteByPromotionId(Integer id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Product not found with ID: " + id);
        }
    }

    @Override
    public Product updateProduct(Integer id, String name, String detail, String image, Double price, Integer quantity, String category) {
        Optional<Product> existingPromotion = productRepository.findById(id);
        if (existingPromotion.isPresent()) {
            Product product = existingPromotion.get();
            product.setProductID(id);
            product.setProductName(name);
            product.setProductDetails(detail);
            product.setProductImage(image);
            product.setPrice(price);
            product.setStockQuantity(quantity);
            product.setCategoryID(category);
            return productRepository.save(product);
        } else {
            throw new EntityNotFoundException("Product not found with ID: " + id);
        }
    }
}
