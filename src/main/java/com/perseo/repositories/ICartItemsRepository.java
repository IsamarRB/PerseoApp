package com.perseo.repositories;

import com.perseo.models.CartItems;
import org.springframework.data.repository.CrudRepository;

public interface ICartItemsRepository extends CrudRepository<CartItems,Integer> {
}
