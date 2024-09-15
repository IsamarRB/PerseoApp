package com.perseo.services;

import com.perseo.models.Cart;
import com.perseo.models.Course;
import com.perseo.repositories.ICartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {
    @Autowired
    private ICartRepository iCartRepository;

    public Iterable<Cart> getAllCarts() {return iCartRepository.findAll();}

    public Optional<Cart> getCartById(int cartId) { return iCartRepository.findById(cartId);}

    public Cart createCart(Cart cart) {return iCartRepository.save(cart);}

    public Cart updateCart(int cartId, Cart updatedCart) {
        return iCartRepository.findById(cartId).map(cart -> {
            cart.setCourses(updatedCart.getCourses());
            cart.setUser(updatedCart.getUser());
            return iCartRepository.save(cart);
        }).orElseThrow(() -> new RuntimeException("Cart not found with id: " + cartId));
    }

    public void deleteCart(int cartId) {iCartRepository.deleteById(cartId);}

    public Cart addCourseToCart(int cartId, Course course) {
        return iCartRepository.findById(cartId).map(cart -> {
            cart.getCourses().add(course);
            return iCartRepository.save(cart);
        }).orElseThrow(() -> new RuntimeException("Cart not found with id: " + cartId));
    }

    public Cart removeCourseFromCart(int cartId, Course course) {
        return iCartRepository.findById(cartId).map(cart -> {
            cart.getCourses().remove(course);
            return iCartRepository.save(cart);
        }).orElseThrow(() -> new RuntimeException("Cart not found with id: " + cartId));
    }
}
