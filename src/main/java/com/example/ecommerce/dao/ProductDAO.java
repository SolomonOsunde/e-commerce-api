package com.example.ecommerce.dao;

import com.example.ecommerce.entity.ProductDetails;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDAO extends CrudRepository<ProductDetails,Integer> {

}
