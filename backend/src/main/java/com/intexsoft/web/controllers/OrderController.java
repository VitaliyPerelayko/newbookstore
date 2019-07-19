package com.intexsoft.web.controllers;

import com.intexsoft.dao.model.Order;
import com.intexsoft.service.entityservice.OrderService;
import com.intexsoft.web.dto.mapping.OrderDTOMapper;
import com.intexsoft.web.dto.request.OrderRequestDTO;
import com.intexsoft.web.dto.response.OrderResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderDTOMapper orderDTOMapper;

    public OrderController(OrderService orderService, OrderDTOMapper orderDTOMapper) {
        this.orderService = orderService;
        this.orderDTOMapper = orderDTOMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(orderDTOMapper.mapOrderToOrderResponseDTO(orderService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<OrderResponseDTO> save(@Valid @RequestBody OrderRequestDTO orderRequestDTO) {
        Assert.isNull(orderRequestDTO.getId(), "Saved order's id must be null");
        Order order = getMappedOrder(orderRequestDTO);
        Order savedOrder = orderService.save(order);
        return ResponseEntity.ok(orderDTOMapper.mapOrderToOrderResponseDTO(savedOrder));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> update(@PathVariable Long id,
                                                   @Valid @RequestBody OrderRequestDTO orderRequestDTO) {
        Assert.state(orderRequestDTO.getId().equals(id),
                "Id in URL path and id in request body must be the same");
        Order order = getMappedOrder(orderRequestDTO);
        Order updatedOrder = orderService.update(order);
        return ResponseEntity.ok(orderDTOMapper.mapOrderToOrderResponseDTO(updatedOrder));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        orderService.delete(id);
    }

    private Order getMappedOrder(OrderRequestDTO orderRequestDTO) {
        Order order = orderDTOMapper.mapOrderRequestDTOToOrder(orderRequestDTO);
        // set dateTime
        order.setDateTime(LocalDateTime.now());
        return order;
    }
}
