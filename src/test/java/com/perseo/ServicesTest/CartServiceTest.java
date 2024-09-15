package com.perseo.ServicesTest;

import com.perseo.models.Cart;
import com.perseo.models.Course;
import com.perseo.repositories.ICartRepository;
import com.perseo.services.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CartServiceTest {
    @Mock
    private ICartRepository iCartRepository;

    @InjectMocks
    private CartService cartService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetCartById() {
        Cart cart = new Cart();
        cart.setCartId(1);
        when(iCartRepository.findById(1)).thenReturn(Optional.of(cart));

        Optional<Cart> foundCart = cartService.getCartById(1);
        assertTrue(foundCart.isPresent());
        assertEquals(1, foundCart.get().getCartId());
    }

    @Test
    void testCreateCart() {
        Cart cart = new Cart();
        when(iCartRepository.save(cart)).thenReturn(cart);

        Cart createdCart = cartService.createCart(cart);
        assertNotNull(createdCart);
        verify(iCartRepository, times(1)).save(cart);
    }

    @Test
    void testAddCourseToCart() {
        Cart cart = new Cart();
        cart.setCourses(new ArrayList<>());

        Course course = new Course();
        when(iCartRepository.findById(1)).thenReturn(Optional.of(cart));
        when(iCartRepository.save(cart)).thenReturn(cart);

        Cart updatedCart = cartService.addCourseToCart(1, course);
        assertEquals(1, updatedCart.getCourses().size());
        verify(iCartRepository, times(1)).save(cart);
    }

    @Test
    void testRemoveCourseFromCart() {
        Cart cart = new Cart();
        Course course = new Course();
        cart.getCourses().add(course);

        when(iCartRepository.findById(1)).thenReturn(Optional.of(cart));
        when(iCartRepository.save(cart)).thenReturn(cart);

        Cart updatedCart = cartService.removeCourseFromCart(1, course);
        assertEquals(0, updatedCart.getCourses().size());
        verify(iCartRepository, times(1)).save(cart);
    }
}
