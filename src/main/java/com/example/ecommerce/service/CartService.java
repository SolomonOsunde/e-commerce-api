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

import java.util.List;
import java.util.stream.Collectors;

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
        List<Cart> cartList = cartDao.findByUser(user);
        List<Cart> filteredList = cartList.stream()
                .filter(x -> x.getProduct().getProductId() == productId)
                .collect(Collectors.toList());

        if(filteredList.size() > 0){
            return null;
        }

        if(product != null && user != null){
            Cart cart = new Cart(product,user);

            return cartDao.save(cart);
        }else {
            return null;
        }
    }

    public List<Cart> getCartList(){
        String userName = JwtRequestFilter.CURRENT_USER;
        UserData user = userDao.findById(userName).get();

        return cartDao.findByUser(user);
    }
    
    public void deleteCartItem(Integer cartId){
        cartDao.deleteById(cartId);
    }
}
