package com.perseo.ControllerTest;

import com.perseo.controllers.CartController;
import com.perseo.models.Cart;
import com.perseo.models.Course;
import com.perseo.services.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class CartControllerTest {
    @Mock
    private CartService cartService;

    @InjectMocks
    private CartController cartController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetCartById() {
        Cart cart = new Cart();
        when(cartService.getCartById(1)).thenReturn(Optional.of(cart));

        ResponseEntity<Cart> response = cartController.getCartById(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testAddCourseToCart() {
        Cart cart = new Cart();
        Course course = new Course();
        when(cartService.addCourseToCart(1, course)).thenReturn(cart);

        ResponseEntity<Cart> response = cartController.addCourseToCart(1, course);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
