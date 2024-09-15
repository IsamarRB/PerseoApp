package com.perseo.services;

import com.perseo.models.Payment;
import com.perseo.repositories.IPaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {
    @Autowired
    private IPaymentRepository iPaymentRepository;

    public Payment createPayment(Payment payment) {return iPaymentRepository.save(payment);}

    public Optional<Payment> getPaymentById(int paymentId) {return iPaymentRepository.findById(paymentId);}

    public List<Payment> getAllPayments() { return (List<Payment>) iPaymentRepository.findAll();}

    public Payment updatePayment(int paymentId, Payment paymentDetails) {
        Optional<Payment> paymentOptional = iPaymentRepository.findById(paymentId);

        if (paymentOptional.isPresent()) {
            Payment payment = paymentOptional.get();
            payment.setPaymentStatus(paymentDetails.getPaymentStatus());
            payment.setAmount(paymentDetails.getAmount());
            payment.setUser(paymentDetails.getUser());
            payment.setCourses(paymentDetails.getCourses());
            return iPaymentRepository.save(payment);
        } else {
            throw new RuntimeException("Payment not found");
        }
    }

    public void deletePayment(int paymentId) {
        iPaymentRepository.deleteById(paymentId);
    }
}