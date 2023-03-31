package com.example.ecommerce.dao;

import com.example.ecommerce.entity.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDAO extends CrudRepository<Product,Integer> {

}
