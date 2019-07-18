package com.intexsoft.service.entityservice.impl;

import com.intexsoft.dao.model.OrderProducts;
import com.intexsoft.dao.repository.OrderProductRepository;
import com.intexsoft.service.entityservice.OrderProductsService;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
public class OrderProductsServiceImpl implements OrderProductsService {

    private final OrderProductRepository orderProductRepository;

    public OrderProductsServiceImpl(OrderProductRepository orderProductRepository) {
        this.orderProductRepository = orderProductRepository;
    }

    @Override
    public OrderProducts saveAndUpdate(@Valid OrderProducts orderProducts){
        return orderProductRepository.saveAndFlush(orderProducts);
    }
}
