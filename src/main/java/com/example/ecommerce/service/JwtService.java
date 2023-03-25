package com.example.ecommerce.service;

import com.example.ecommerce.dao.UserDao;
import com.example.ecommerce.entity.JwtRequest;
import com.example.ecommerce.entity.JwtResponse;
import com.example.ecommerce.entity.UserData;
import com.example.ecommerce.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class JwtService implements UserDetailsService {


    @Autowired
    private UserDao userDao;
    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserData userData = userDao.findById(username).get();

        if(userData != null){
            return new User(userData.getUsername(),userData.getPassword(),getAuthorites(userData));
        }else{
            throw new UsernameNotFoundException("User ID not found with UserID: " + username);
        }
    }
    private Set getAuthorites(UserData userData){
        Set<SimpleGrantedAuthority> authorites = new HashSet<>();

        userData.getRole().forEach(role -> {
            authorites.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
        });
        return authorites;
    }
    public JwtResponse createJwtToken(JwtRequest jwtRequest) throws Exception{
        String userName = jwtRequest.getUserName();
        String userPassword = jwtRequest.getUserPassword();

        authenticate(userName,userPassword);

        UserDetails userDetails = loadUserByUsername(userName);
        String newGeneratedToken = jwtUtil.generateToken(userDetails);

        UserData user = userDao.findById(userName).get();

        return new JwtResponse(user,newGeneratedToken);
    }

    private void authenticate(String userName, String userPassword) throws Exception{
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName,userPassword));
        }catch(DisabledException e){
            throw new DisabledException("User is Diabled",e);
        }catch(BadCredentialsException e){
            throw new BadCredentialsException("Bad credentials from User",e);
        }
    }

}
