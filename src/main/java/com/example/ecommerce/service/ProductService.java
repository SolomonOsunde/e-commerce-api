package com.example.ecommerce.service;

import com.example.ecommerce.dao.ProductDAO;
import com.example.ecommerce.entity.ImageModel;
import com.example.ecommerce.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ProductService {
    @Autowired
    private ProductDAO productDAO;

    public Product addNewProduct(Product product){
        return productDAO.save(product);
    }
    public Set<ImageModel> uploadImage(MultipartFile[] multipartFiles) throws IOException {
        Set<ImageModel> imageModelSet = new HashSet<>();

        for(MultipartFile file :multipartFiles){
            ImageModel imageModel = new ImageModel(
                    file.getOriginalFilename(), file.getContentType(), file.getBytes()
            );
            imageModelSet.add(imageModel);
        }
        return imageModelSet;
    }

    public Product getProductDetailsById(Integer productId){
        if (productDAO.findById(productId).isPresent()) {
            return productDAO.findById(productId).get();
        }else {
            return null;
        }
    }
    public List<Product> getAllProducts(){
        return (List<Product>) productDAO.findAll();
    }

    public void deleteProductDetails(Integer productId){
        productDAO.deleteById(productId);
    }

}
