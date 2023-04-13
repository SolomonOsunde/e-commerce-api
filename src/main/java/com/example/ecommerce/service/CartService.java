package com.example.ecommerce.service;

import com.example.ecommerce.configuration.JwtRequestFilter;
import com.example.ecommerce.dao.CartDao;
import com.example.ecommerce.dao.ProductDAO;
import com.example.ecommerce.dao.UserDao;
import com.example.ecommerce.entity.Cart;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.entity.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    @Autowired
    private CartDao cartDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private ProductDAO productDAO;
    public Cart addToCart(Integer productId){
        Product product = productDAO.findById(productId).get();
        String currentUser = JwtRequestFilter.CURRENT_USER;

        UserData user = null;
        if(currentUser != null) {
            user = userDao.findById(currentUser).get();
        }

        if(product != null && user != null){
            Cart cart = new Cart(product,user);

            return cartDao.save(cart);
        }else {
            return null;
        }
    }
}
