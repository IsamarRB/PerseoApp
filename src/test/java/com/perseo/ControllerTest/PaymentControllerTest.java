package com.perseo.ControllerTest;

import com.perseo.controllers.PaymentController;
import com.perseo.models.Payment;
import com.perseo.services.PaymentService;
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

public class PaymentControllerTest {
    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private PaymentController paymentController;

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
        when(paymentService.createPayment(payment)).thenReturn(payment);

        Payment createdPayment = paymentController.createPayment(payment);

        assertNotNull(createdPayment);
        assertEquals(payment.getPaymentId(), createdPayment.getPaymentId());
        verify(paymentService, times(1)).createPayment(payment);
    }

    @Test
    void testGetPaymentById() {
        when(paymentService.getPaymentById(1)).thenReturn(Optional.of(payment));

        ResponseEntity<Payment> response = paymentController.getPaymentById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(payment.getPaymentId(), response.getBody().getPaymentId());
        verify(paymentService, times(1)).getPaymentById(1);
    }

    @Test
    void testGetAllPayments() {
        List<Payment> payments = new ArrayList<>();
        payments.add(payment);

        when(paymentService.getAllPayments()).thenReturn(payments);

        List<Payment> allPayments = paymentController.getAllPayments();

        assertNotNull(allPayments);
        assertEquals(1, allPayments.size());
        verify(paymentService, times(1)).getAllPayments();
    }

    @Test
    void testUpdatePayment() {
        when(paymentService.updatePayment(1, payment)).thenReturn(payment);

        ResponseEntity<Payment> response = paymentController.updatePayment(1, payment);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(payment.getPaymentId(), response.getBody().getPaymentId());
        verify(paymentService, times(1)).updatePayment(1, payment);
    }

    @Test
    void testDeletePayment() {
        doNothing().when(paymentService).deletePayment(1);

        ResponseEntity<Void> response = paymentController.deletePayment(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(paymentService, times(1)).deletePayment(1);
    }
}
