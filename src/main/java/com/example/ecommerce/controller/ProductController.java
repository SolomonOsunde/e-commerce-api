package com.example.ecommerce.controller;

import com.example.ecommerce.entity.ProductDetails;
import com.example.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;
    @PostMapping({"/addNewProduct"})
    private ProductDetails addNewProduct(@RequestBody ProductDetails product){
        return productService.addNewProduct(product);
    }
}
