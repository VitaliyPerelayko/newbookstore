package com.intexsoft.service.entityservice.impl;

import com.intexsoft.dao.model.Order;
import com.intexsoft.dao.repository.OrderRepository;
import com.intexsoft.service.entityservice.OrderService;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order findById(Long id) {
        return orderRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Order with the given id isn't exist"));
    }

    @Override
    public Order save(@Valid Order order) {
        if (order.getId() != null) {
            throw new IllegalStateException("Order which is saved must hasn't id");
        }
        return orderRepository.saveAndFlush(order);
    }

    @Override
    public Order update(@Valid Order order){
        if (order.getId() == null) {
            throw new IllegalStateException("Order which is updated must has id");
        }
        return orderRepository.saveAndFlush(order);
    }

    @Override
    public void delete(Long id){
        orderRepository.deleteById(id);
    }
}
