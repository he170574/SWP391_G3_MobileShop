package com.mobile_shop.service.impl;

import com.mobile_shop.dto.ProductDTO;
import com.mobile_shop.entity.Product;
import com.mobile_shop.repository.ProductRepository;
import com.mobile_shop.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }



    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> getOne(Integer id) {
        return productRepository.findById(id);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public Product parseProductDtoToProduct(ProductDTO productDTO) {
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

    @Override
    public void deleteByProductId(Integer id) {
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
            product.setCategoryID(category);
            product.setStockQuantity(quantity);
            return productRepository.save(product);
        } else {
            throw new EntityNotFoundException("Product not found with ID: " + id);
        }
    }
}
