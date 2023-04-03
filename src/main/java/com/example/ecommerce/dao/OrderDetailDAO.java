package com.example.ecommerce.dao;

import com.example.ecommerce.entity.OrderDetails;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailDAO extends CrudRepository<OrderDetails,Integer> {
}
