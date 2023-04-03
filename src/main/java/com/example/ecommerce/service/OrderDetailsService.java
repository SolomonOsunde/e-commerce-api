package com.example.ecommerce.service;

import com.example.ecommerce.configuration.JwtRequestFilter;
import com.example.ecommerce.dao.OrderDetailDAO;
import com.example.ecommerce.dao.ProductDAO;
import com.example.ecommerce.dao.UserDao;
import com.example.ecommerce.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OrderDetailsService {

    private static final String ORDER_PLACED = "Placed Order";
    @Autowired
    private OrderDetailDAO orderDetailDAO;

    @Autowired
    private ProductDAO productDAO;

    @Autowired
    private UserDao userDao;

    public void placeOrder(OrderInput orderInput){
        List<OrderProductQuantity> productQuantityList =orderInput.getOrderProductQuantityList();

        for(OrderProductQuantity order : productQuantityList){


            Product product = productDAO.findById(order.getProductId()).get();
            String currentUser = JwtRequestFilter.CURRENT_USER;
            UserData userData = userDao.findById(currentUser).get();

            OrderDetails orderDetail = new OrderDetails(
                    orderInput.getFullName(),
                    orderInput.getFullAddress(),
                    orderInput.getContactNumber(),
                    orderInput.getAlternateContactNumber(),
                    ORDER_PLACED,
                    product.getProductActualPrice()*order.getQuantity(),
                    product,
                    userData
            );

            orderDetailDAO.save(orderDetail);
        }

    }
}
