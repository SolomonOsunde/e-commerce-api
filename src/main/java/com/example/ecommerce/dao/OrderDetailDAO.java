package com.example.ecommerce.dao;

import com.example.ecommerce.entity.OrderDetails;
import com.example.ecommerce.entity.UserData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface OrderDetailDAO extends CrudRepository<OrderDetails,Integer> {

     List<OrderDetails> findByUserData(UserData userData);
     List<OrderDetails> findByOrderStatus(String status);
}
