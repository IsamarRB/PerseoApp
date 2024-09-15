package com.perseo.ControllerTest;

import com.perseo.controllers.CartItemsController;
import com.perseo.models.CartItems;
import com.perseo.services.CartItemsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class CartItemsControllerTest {
    @Mock
    private CartItemsService cartItemsService;

    @InjectMocks
    private CartItemsController cartItemsController;

    private CartItems cartItems;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cartItems = CartItems.builder()
                .cartItemsId(1)
                .quantity(2)
                .build();
    }

    @Test
    void testGetAllCartItems() {
        List<CartItems> cartItemsList = new ArrayList<>();
        cartItemsList.add(cartItems);

        when(cartItemsService.getAllCartItems()).thenReturn(cartItemsList);

        List<CartItems> result = cartItemsController.getAllCartItems();

        assertEquals(1, result.size());
        verify(cartItemsService, times(1)).getAllCartItems();
    }

    @Test
    void testGetCartItemsById() {
        when(cartItemsService.getCartItemsById(1)).thenReturn(Optional.of(cartItems));

        ResponseEntity<CartItems> response = cartItemsController.getCartItemsById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(cartItems.getCartItemsId(), response.getBody().getCartItemsId());
        verify(cartItemsService, times(1)).getCartItemsById(1);
    }

    @Test
    void testGetCartItemsById_NotFound() {
        when(cartItemsService.getCartItemsById(1)).thenReturn(Optional.empty());

        ResponseEntity<CartItems> response = cartItemsController.getCartItemsById(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(cartItemsService, times(1)).getCartItemsById(1);
    }

    @Test
    void testCreateCartItems() {
        when(cartItemsService.createCartItems(cartItems)).thenReturn(cartItems);

        CartItems createdItem = cartItemsController.createCartItems(cartItems);

        assertNotNull(createdItem);
        assertEquals(cartItems.getCartItemsId(), createdItem.getCartItemsId());
        verify(cartItemsService, times(1)).createCartItems(cartItems);
    }

    @Test
    void testUpdateCartItems() {
        when(cartItemsService.updateCartItems(1, cartItems)).thenReturn(cartItems);

        ResponseEntity<CartItems> response = cartItemsController.updateCartItems(1, cartItems);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(cartItems.getQuantity(), response.getBody().getQuantity());
        verify(cartItemsService, times(1)).updateCartItems(1, cartItems);
    }

    @Test
    void testUpdateCartItems_NotFound() {
        when(cartItemsService.updateCartItems(1, cartItems)).thenThrow(new RuntimeException("CartItems not found with id: 1"));

        ResponseEntity<CartItems> response = cartItemsController.updateCartItems(1, cartItems);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(cartItemsService, times(1)).updateCartItems(1, cartItems);
    }

    @Test
    void testDeleteCartItems() {
        doNothing().when(cartItemsService).deleteCartItems(1);

        ResponseEntity<Void> response = cartItemsController.deleteCartItems(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(cartItemsService, times(1)).deleteCartItems(1);
    }
}
