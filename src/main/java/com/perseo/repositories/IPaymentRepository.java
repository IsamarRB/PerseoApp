package com.perseo.repositories;

import com.perseo.models.Payment;
import org.springframework.data.repository.CrudRepository;

public interface IPaymentRepository extends CrudRepository<Payment,Integer> {
}
