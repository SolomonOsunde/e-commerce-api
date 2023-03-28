package com.example.ecommerce.service;

import com.example.ecommerce.dao.ProductDAO;
import com.example.ecommerce.entity.ProductDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    @Autowired
    private ProductDAO productDAO;

    public ProductDetails addNewProduct(ProductDetails product){
        return productDAO.save(product);
    }
}
