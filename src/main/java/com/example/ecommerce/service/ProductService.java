package com.example.ecommerce.service;

import com.example.ecommerce.configuration.JwtRequestFilter;
import com.example.ecommerce.dao.CartDao;
import com.example.ecommerce.dao.ProductDAO;
import com.example.ecommerce.dao.UserDao;
import com.example.ecommerce.entity.Cart;
import com.example.ecommerce.entity.ImageModel;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.entity.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductDAO productDAO;
    @Autowired
    private UserDao userDao;
    @Autowired
    private CartDao cartDao;

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
    public List<Product> getAllProducts(int pageNumber,String searchKey){
        Pageable pageable = PageRequest.of(pageNumber,12);

        if(searchKey.equals("")){
            return productDAO.findAll(pageable);
        }else{
            return productDAO.findByProductNameContainingIgnoreCaseOrProductDescriptionContainingIgnoreCase(searchKey,searchKey,pageable);
        }

    }

    public void deleteProductDetails(Integer productId){
        productDAO.deleteById(productId);
    }
    public List<Product> getProductDetails(boolean isSingleProductCheckout, Integer productId){
        if(isSingleProductCheckout && productId != 0){
            //we are going to buy a single product
            List<Product> list =new ArrayList<>();
            Product product = productDAO.findById(productId).get();
            list.add(product);
            return list;

        }else{
            //we are going to check out the entire cart
            String currentUser = JwtRequestFilter.CURRENT_USER;
            UserData user = userDao.findById(currentUser).get();

            List<Cart> userCart = cartDao.findByUser(user);

           return userCart.stream()
                    .map(x ->x.getProduct())
                    .collect(Collectors.toList());
        }
    }

}
