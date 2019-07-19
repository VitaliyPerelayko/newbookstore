package com.intexsoft.service.entityservice;

import com.intexsoft.dao.model.OrderProductId;
import com.intexsoft.dao.model.OrderProducts;

public interface OrderProductsService {
    OrderProducts saveAndUpdate(OrderProducts orderProducts);

    void delete(OrderProductId id);
}
