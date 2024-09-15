package com.perseo.repositories;

import com.perseo.models.Cart;
import org.springframework.data.repository.CrudRepository;

public interface ICartRepository extends CrudRepository<Cart, Integer> {
}
