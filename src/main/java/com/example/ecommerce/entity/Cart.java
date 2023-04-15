package com.example.ecommerce.entity;

import javax.persistence.*;

@Entity
@Table
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer cartId;
    @OneToOne
    private Product product;
    @OneToOne
    private UserData user;

    public Cart(Product product, UserData user) {
        this.product = product;
        this.user = user;
    }

    public Cart() {

    }

    public Integer getCartId() {
        return cartId;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public UserData getUser() {
        return user;
    }

    public void setUser(UserData userData) {
        this.user = userData;
    }
}
