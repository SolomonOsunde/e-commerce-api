package com.example.ecommerce.controller;

import com.example.ecommerce.entity.OrderDetails;
import com.example.ecommerce.entity.OrderInput;
import com.example.ecommerce.entity.TransactionDetails;
import com.example.ecommerce.service.OrderDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderDetailsController {

    @Autowired
    private OrderDetailsService orderDetailsService;

    @PreAuthorize("hasRole('User')")
    @PostMapping({"/placeOrder/{isSingleProductCheckout}"})
    public void placeOrder(
            @PathVariable(name = "isSingleProductCheckout") boolean isSingleProductCheckout,
            @RequestBody OrderInput orderInput){
        orderDetailsService.placeOrder(orderInput,isSingleProductCheckout);
    }
    @PreAuthorize("hasRole('User')")
    @GetMapping({"/getOrderDetails"})
    public List<OrderDetails> getOrderDetails(){
        return orderDetailsService.getOrderDetails();
    }
    @PreAuthorize("hasRole('Admin')")
    @GetMapping({"/getAllOrderDetails/{status}"})
    public List<OrderDetails> getAllOrderDetails(@PathVariable(name = "status")String status){
        return orderDetailsService.getAllOrderDetails(status);
    }
    @PreAuthorize("hasRole('Admin')")
    @GetMapping({"/markOrderAsDelivered/{orderId}"})
    public void markOrderAsDelivered(@PathVariable(name = "orderId")Integer orderId){
        orderDetailsService.markOrderAsDelivered(orderId);
    }

    @PreAuthorize("hasRole('User')")
    @GetMapping({"/createTransaction/{amount)"})
    public TransactionDetails createTransaction(@PathVariable(name = "amount") Double amount){
        return orderDetailsService.createTransaction(amount);
    }
}
