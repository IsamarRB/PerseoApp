package com.perseo.ServicesTest;

import com.perseo.models.Payment;
import com.perseo.repositories.IPaymentRepository;
import com.perseo.services.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PaymentServiceTest {
    @Mock
    private IPaymentRepository iPaymentRepository;

    @InjectMocks
    private PaymentService paymentService;

    private Payment payment;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        payment = new Payment();
        payment.setPaymentId(1);
        payment.setPaymentStatus("Completed");
        payment.setAmount(100.0);
    }

    @Test
    void testCreatePayment() {
        when(iPaymentRepository.save(payment)).thenReturn(payment);

        Payment createdPayment = paymentService.createPayment(payment);

        assertNotNull(createdPayment);
        assertEquals(payment.getPaymentId(), createdPayment.getPaymentId());
        verify(iPaymentRepository, times(1)).save(payment);
    }

    @Test
    void testGetPaymentById() {
        when(iPaymentRepository.findById(1)).thenReturn(Optional.of(payment));

        Optional<Payment> foundPayment = paymentService.getPaymentById(1);

        assertTrue(foundPayment.isPresent());
        assertEquals(payment.getPaymentId(), foundPayment.get().getPaymentId());
        verify(iPaymentRepository, times(1)).findById(1);
    }

    @Test
    void testUpdatePayment() {
        when(iPaymentRepository.findById(1)).thenReturn(Optional.of(payment));
        when(iPaymentRepository.save(payment)).thenReturn(payment);

        Payment updatedPayment = paymentService.updatePayment(1, payment);

        assertEquals(payment.getPaymentStatus(), updatedPayment.getPaymentStatus());
        verify(iPaymentRepository, times(1)).save(payment);
    }

    @Test
    void testDeletePayment() {
        paymentService.deletePayment(1);
        verify(iPaymentRepository, times(1)).deleteById(1);
    }
}
