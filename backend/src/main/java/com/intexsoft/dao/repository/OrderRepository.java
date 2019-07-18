package com.intexsoft.dao.repository;

import com.intexsoft.dao.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
