package com.mobile_shop.controller;

import com.mobile_shop.dto.ProductDTO;
import com.mobile_shop.dto.ResponseDTO;
import com.mobile_shop.entity.Product;
import com.mobile_shop.repository.ProductRepository;
import com.mobile_shop.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {
    private final ProductRepository productRepository;
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService, ProductRepository productRepository) {
        this.productService = productService;
        this.productRepository = productRepository;
    }

    @GetMapping("/get-product")
    public ResponseEntity<ResponseDTO> getAllProducts() {
        List<Product> products = productService.getAll();
        List<ProductDTO> productDTOs = products.stream().map(item -> ProductDTO.builder()
                .productId(item.getProductID())
                .productName(item.getProductName())
                .productDetails(item.getProductDetails())
                .productImage(item.getProductImage())
                .price(item.getPrice())
                .CategoryID(item.getCategoryID())
                .stockQuantity(item.getStockQuantity())
                .build()).toList();
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Get success");
        responseDTO.setData(productDTOs);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @PostMapping("/edit-product")
    public ResponseEntity<ResponseDTO> getProductById(@Valid @RequestBody ProductDTO productDTO, BindingResult bindingResult) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Get success");
        responseDTO.setData(productService.getOne(productDTO.getProductId()));
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @PostMapping("/save-new-product")
    public ResponseEntity<ResponseDTO> saveProduct(@RequestBody ProductDTO productDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        Product product = productService.parseProductDtoToProduct(productDTO);
        responseDTO.setMessage("Get success");
        responseDTO.setData(productService.save(product));
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @PostMapping("/delete-product")
    public ResponseEntity<ResponseDTO> deleteProduct(@RequestParam Long id) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            productService.deleteByProductId(id);
            responseDTO.setMessage("Success");
            responseDTO.setData("Delete success"); // No data needed for delete response
            return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
        } catch (Exception e) {
            responseDTO.setMessage("Delete failed: " + e.getMessage());
            responseDTO.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
        }
    }

    @PostMapping("/submitForm")
    public String submitForm(@Valid @RequestBody ProductDTO productDTO, BindingResult bindingResult) {
        // Add any custom validation logic here
        if (bindingResult.hasErrors()) {
            return "Validation failed";
        }
        return "Form submitted successfully";
    }

    @PostMapping("/update-product")
    public ResponseEntity<ResponseDTO> updateProduct(@RequestBody ProductDTO productDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        Product product = productService.updateProduct(
                productDTO.getProductId(),
                productDTO.getProductName(),
                productDTO.getProductDetails(),
                productDTO.getProductImage(),
                productDTO.getPrice(),
                productDTO.getStockQuantity(),
                productDTO.getCategoryID());
        responseDTO.setMessage("Get success");
        responseDTO.setData(product);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }
}