package com.intexsoft.dao.repository;

import com.intexsoft.dao.model.OrderProductId;
import com.intexsoft.dao.model.OrderProducts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductRepository extends JpaRepository<OrderProducts, OrderProductId> {
}
