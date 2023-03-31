package com.example.ecommerce.controller;

import com.example.ecommerce.entity.ImageModel;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@RestController
public class ProductController {
    @Autowired
    private ProductService productService;

    @PreAuthorize("hasRole('Admin')")
    @PostMapping(value = {"/addNewProduct"},consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Product addNewProduct(@RequestPart("product") Product product,
                                  @RequestPart("imageFile") MultipartFile[] file){

        try {
            Set<ImageModel> images = productService.uploadImage(file);
            product.setProductImages(images);
            return productService.addNewProduct(product);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @PreAuthorize("hasRole('Admin')")
    @GetMapping({"/getAllProducts"})
    public List<Product> getAllProducts(){
        return productService.getAllProducts();
    }

    @PreAuthorize("hasRole('Admin')")
    @GetMapping({"/getProductById/{productId}"})
    public Product getProductById(@PathVariable("productId") Integer productId){
        return productService.getProductDetailsById(productId);
    }


    @PreAuthorize("hasRole('Admin')")
    @DeleteMapping({"/deleteById/{productId}"})
    public void deleteById(@PathVariable("productId") Integer productId){
        productService.deleteProductDetails(productId);
    }

}
