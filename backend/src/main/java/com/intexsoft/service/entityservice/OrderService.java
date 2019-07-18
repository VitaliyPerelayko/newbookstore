package com.intexsoft.service.entityservice;

import com.intexsoft.dao.model.Order;

public interface OrderService {
    Order findById(Long id);

    Order save(Order order);

    Order update(Order order);

    void delete(Long id);
}
