package com.perseo.ServicesTest;

import com.perseo.models.CartItems;
import com.perseo.repositories.ICartItemsRepository;
import com.perseo.services.CartItemsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CartItemsServiceTest {
    @Mock
    private ICartItemsRepository cartItemsRepository;

    @InjectMocks
    private CartItemsService cartItemsService;

    private CartItems cartItems;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cartItems = CartItems.builder()
                .cartItemsId(1)
                .quantity(3)
                .build();
    }

    @Test
    void testGetAllCartItems() {
        List<CartItems> cartItemsList = new ArrayList<>();
        cartItemsList.add(cartItems);

        when(cartItemsRepository.findAll()).thenReturn(cartItemsList);

        List<CartItems> result = cartItemsService.getAllCartItems();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(cartItemsRepository, times(1)).findAll();
    }

    @Test
    void testGetCartItemsById() {
        when(cartItemsRepository.findById(1)).thenReturn(Optional.of(cartItems));

        Optional<CartItems> result = cartItemsService.getCartItemsById(1);

        assertTrue(result.isPresent());
        assertEquals(cartItems.getCartItemsId(), result.get().getCartItemsId());
        verify(cartItemsRepository, times(1)).findById(1);
    }

    @Test
    void testGetCartItemsById_NotFound() {
        when(cartItemsRepository.findById(1)).thenReturn(Optional.empty());

        Optional<CartItems> result = cartItemsService.getCartItemsById(1);

        assertFalse(result.isPresent());
        verify(cartItemsRepository, times(1)).findById(1);
    }

    @Test
    void testCreateCartItems() {
        when(cartItemsRepository.save(cartItems)).thenReturn(cartItems);

        CartItems result = cartItemsService.createCartItems(cartItems);

        assertNotNull(result);
        assertEquals(cartItems.getCartItemsId(), result.getCartItemsId());
        verify(cartItemsRepository, times(1)).save(cartItems);
    }

    @Test
    void testUpdateCartItems_Success() {
        CartItems updatedCartItems = CartItems.builder()
                .cartItemsId(1)
                .quantity(5)
                .build();

        when(cartItemsRepository.findById(1)).thenReturn(Optional.of(cartItems));
        when(cartItemsRepository.save(cartItems)).thenReturn(updatedCartItems);

        CartItems result = cartItemsService.updateCartItems(1, updatedCartItems);

        assertNotNull(result);
        assertEquals(5, result.getQuantity());
        verify(cartItemsRepository, times(1)).findById(1);
        verify(cartItemsRepository, times(1)).save(cartItems);
    }

    @Test
    void testUpdateCartItems_NotFound() {
        CartItems updatedCartItems = CartItems.builder()
                .cartItemsId(1)
                .quantity(5)
                .build();

        when(cartItemsRepository.findById(1)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            cartItemsService.updateCartItems(1, updatedCartItems);
        });

        assertEquals("CartItems not found with id: 1", exception.getMessage());
        verify(cartItemsRepository, times(1)).findById(1);
        verify(cartItemsRepository, never()).save(any(CartItems.class));
    }

    @Test
    void testDeleteCartItems() {
        doNothing().when(cartItemsRepository).deleteById(1);

        cartItemsService.deleteCartItems(1);

        verify(cartItemsRepository, times(1)).deleteById(1);
    }
}
