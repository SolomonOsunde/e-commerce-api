package com.example.ecommerce.service;

import com.example.ecommerce.dao.RoleDao;
import com.example.ecommerce.dao.UserDao;
import com.example.ecommerce.entity.Role;
import com.example.ecommerce.entity.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private PasswordEncoder passwordEncoder;

//    public UserData registerNewUser(UserData userData){
//        Role userRole = roleDao.findById("User").get();
//
//        Set<Role> roles =new HashSet<>();
//        roles.add(userRole);
//
//        userData.setRole(roles);
//        userData.setPassword(getEncodedPassword(userData.getPassword()));
//
//       return userDao.save(userData);
//    }

    public void initRolesAndUsers(){
        Role adminRole = new Role();
        adminRole.setRoleName("Admin");
        adminRole.setRoleDescription("Admin Role");
        roleDao.save(adminRole);

        Role userRole = new Role();
        userRole.setRoleName("User");
        userRole.setRoleDescription("Customer here to buy and sell");
        roleDao.save(userRole);

        UserData adminUser = new UserData();
        adminUser.setFirstname("Admin");
        adminUser.setLastname("User");
        adminUser.setUsername("admin@gmail.com");
        adminUser.setPassword(getEncodedPassword("password"));
        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(adminRole);
        adminUser.setRole(adminRoles);
        userDao.save(adminUser);

        UserData user_data = new UserData();
        user_data.setFirstname("Solomon");
        user_data.setLastname("Osunde");
        user_data.setUsername("Solomon@gmail.com");
        user_data.setPassword(getEncodedPassword("password"));
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(userRole);
        user_data.setRole(userRoles);
        userDao.save(user_data);

    }
    public UserData registerNewUser(UserData user){

        Role userRole = roleDao.findById("User").get();

        Set<Role> roleSet = new HashSet<>();
        roleSet.add(userRole);
        user.setRole(roleSet);

        String encodedPassword = getEncodedPassword(user.getPassword());
        user.setPassword(encodedPassword);


        return userDao.save(user);
    }

    public String getEncodedPassword(String password){
        return passwordEncoder.encode(password);
    }
}
