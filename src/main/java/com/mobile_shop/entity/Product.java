package com.mobile_shop.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "PRODUCT", schema = "MobileShop")
public class Product {
    @Id
    @Column(name = "ProductID")
    private Integer productID;

    @Column(name = "ProductName")
    private String productName;

    @Column(name = "ProductDetails")
    private String productDetails;

    @Column(name = "ProductImage")
    private String productImage;

    @Column(name = "Price")
    private Double price;

    @Column(name = "CategoryID")
    private String CategoryID;

    @Column(name = "StockQuantity")
    private Integer stockQuantity;

    @Column(name = "DELETED", nullable = false)
    private Boolean deleted;

}
