package com.perseo.controllers;

import com.perseo.models.CartItems;
import com.perseo.services.CartItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

public class CartItemsController {
    @Autowired
    private CartItemsService cartItemsService;

    @GetMapping
    public List<CartItems> getAllCartItems() {return cartItemsService.getAllCartItems();}

    @GetMapping("/{id}")
    public ResponseEntity<CartItems> getCartItemsById(@PathVariable int id) {Optional<CartItems> cartItems = cartItemsService.getCartItemsById(id);
        return cartItems.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());}

    @PostMapping
    public CartItems createCartItems(@RequestBody CartItems cartItems) {return cartItemsService.createCartItems(cartItems);}

    @PutMapping("/{id}")
    public ResponseEntity<CartItems> updateCartItems(@PathVariable int id, @RequestBody CartItems updatedCartItems) {
        try {
            CartItems updatedItem = cartItemsService.updateCartItems(id, updatedCartItems);
            return ResponseEntity.ok(updatedItem);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCartItems(@PathVariable int id) {
        cartItemsService.deleteCartItems(id);
        return ResponseEntity.noContent().build();
    }
}
