package com.perseo.controllers;

import com.perseo.models.Cart;
import com.perseo.models.Course;
import com.perseo.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/carts")

public class CartController {
    @Autowired
    private CartService cartService;

    // Get all carts
    @GetMapping
    public ResponseEntity<List<Cart>> getAllCarts() {
        List<Cart> carts = (List<Cart>) cartService.getAllCarts();
        return new ResponseEntity<>(carts, HttpStatus.OK);
    }

    // Get cart by ID
    @GetMapping("/{id}")
    public ResponseEntity<Cart> getCartById(@PathVariable("id") int cartId) {
        Optional<Cart> cart = cartService.getCartById(cartId);
        return cart.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Create a new cart
    @PostMapping
    public ResponseEntity<Cart> createCart(@RequestBody Cart cart) {
        Cart createdCart = cartService.createCart(cart);
        return new ResponseEntity<>(createdCart, HttpStatus.CREATED);
    }

    // Update an existing cart
    @PutMapping("/{id}")
    public ResponseEntity<Cart> updateCart(@PathVariable("id") int cartId, @RequestBody Cart cart) {
        try {
            Cart updatedCart = cartService.updateCart(cartId, cart);
            return new ResponseEntity<>(updatedCart, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete a cart
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCart(@PathVariable("id") int cartId) {
        cartService.deleteCart(cartId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Add course to the cart
    @PostMapping("/{id}/courses")
    public ResponseEntity<Cart> addCourseToCart(@PathVariable("id") int cartId, @RequestBody Course course) {
        try {
            Cart updatedCart = cartService.addCourseToCart(cartId, course);
            return new ResponseEntity<>(updatedCart, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Remove course from the cart
    @DeleteMapping("/{id}/courses")
    public ResponseEntity<Cart> removeCourseFromCart(@PathVariable("id") int cartId, @RequestBody Course course) {
        try {
            Cart updatedCart = cartService.removeCourseFromCart(cartId, course);
            return new ResponseEntity<>(updatedCart, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
