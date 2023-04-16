package com.example.ecommerce.service;

import com.example.ecommerce.configuration.JwtRequestFilter;
import com.example.ecommerce.dao.CartDao;
import com.example.ecommerce.dao.OrderDetailDAO;
import com.example.ecommerce.dao.ProductDAO;
import com.example.ecommerce.dao.UserDao;
import com.example.ecommerce.entity.*;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class OrderDetailsService {

    private static final String ORDER_PLACED = "Placed Order";
    private static final String KEY = "rzp_test_kcpmRWwxH5eoo1";
    private static final String KEY_SECRET= "CMtMUUoDjWDG8JWveuLLMkAY";
    private static final String CURRENCY= "INR";


    @Autowired
    private OrderDetailDAO orderDetailDAO;

    @Autowired
    private ProductDAO productDAO;

    @Autowired
    private UserDao userDao;
    @Autowired
    private CartDao cartDao;

    public void placeOrder(OrderInput orderInput, boolean isSingleProductCheckout) {
        List<OrderProductQuantity> productQuantityList = orderInput.getOrderProductQuantityList();

        for (OrderProductQuantity order : productQuantityList) {

            Product product = productDAO.findById(order.getProductId()).get();
            String currentUser = JwtRequestFilter.CURRENT_USER;
            UserData userData = userDao.findById(currentUser).get();

            OrderDetails orderDetail = new OrderDetails(
                    orderInput.getFullName(),
                    orderInput.getFullAddress(),
                    orderInput.getContactNumber(),
                    orderInput.getAlternateContactNumber(),
                    ORDER_PLACED,
                    product.getProductActualPrice() * order.getQuantity(),
                    product,
                    userData
            );

            if (!isSingleProductCheckout) {
                List<Cart> userCart = cartDao.findByUser(userData);
                userCart.stream()
                        .forEach((cart -> cartDao.deleteById(cart.getCartId())));
            }

            orderDetailDAO.save(orderDetail);
        }
    }

    public List<OrderDetails> getOrderDetails(){
        String currentUser = JwtRequestFilter.CURRENT_USER;
        UserData user = userDao.findById(currentUser).get();

        return orderDetailDAO.findByUserData(user);

    }

    public List<OrderDetails> getAllOrderDetails(String status) {
        List<OrderDetails> orderDetailsList = new ArrayList<>();

        if(status.equals("all")){
            orderDetailDAO.findAll().forEach(
                    orderDetailsList::add
            );
        }else{
            orderDetailDAO.findByOrderStatus(status)
                    .forEach(x -> orderDetailsList.add(x));
        }


        return orderDetailsList;
    }

    public void markOrderAsDelivered(Integer orderId) {
        OrderDetails orderDetails = orderDetailDAO.findById(orderId).get();
        if(orderDetails != null){
            orderDetails.setOrderStatus("Delivered");
            orderDetailDAO.save(orderDetails);
        }
    }
    public TransactionDetails createTransaction(Double amount){
     try {

         JSONObject jsonObject = new JSONObject();
         jsonObject.put("amount",(amount * 100));
         jsonObject.put("currency",CURRENCY);

         RazorpayClient razorpayClient = new RazorpayClient(KEY,KEY_SECRET);

         Order order = razorpayClient.orders.create(jsonObject);
         return prepareTransactionDetails(order);


     }catch (Exception e){
         System.out.println(e.getMessage());

     }
        return null;
    }

    private TransactionDetails prepareTransactionDetails(Order order){
        String orderId = order.get("id");
        String currency = order.get("currency");
        Integer amount = order.get("amount");

        TransactionDetails transactionDetails = new TransactionDetails(orderId,currency,amount);
        return transactionDetails;

    }
}

