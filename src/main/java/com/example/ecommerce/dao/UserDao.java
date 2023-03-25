package com.example.ecommerce.dao;

import com.example.ecommerce.entity.UserData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends CrudRepository<UserData,String> {

}
