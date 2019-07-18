package com.intexsoft.web.dto.mapping;

import com.intexsoft.dao.model.Order;
import com.intexsoft.dao.model.OrderProducts;
import com.intexsoft.dao.model.User;
import com.intexsoft.service.entityservice.UserService;
import com.intexsoft.web.dto.request.OrderRequestDTO;
import com.intexsoft.web.dto.response.BookResponseForOrderDTO;
import com.intexsoft.web.dto.response.OrderResponseDTO;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.stream.Collectors;

@Service
public class OrderDTOMapper {

    private final BookDTOMapper bookDTOMapper;
    private final UserService userService;

    public OrderDTOMapper(BookDTOMapper bookDTOMapper, UserService userService) {
        this.bookDTOMapper = bookDTOMapper;
        this.userService = userService;
    }

    public OrderResponseDTO mapOrderToOrderResponseDTO(@Valid Order order) {
        OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
        orderResponseDTO.setAddress(order.getDeliveryAddress());
        orderResponseDTO.setBooks(order.getOrderProducts().stream()
                .map(this::mapOrderProductsToBookResponseForOrderDTO).collect(Collectors.toList()));
        orderResponseDTO.setComment(order.getComment());
        orderResponseDTO.setDateTime(order.getDateTime());
        orderResponseDTO.setId(order.getId());
        orderResponseDTO.setPaid(order.getPaid());
        orderResponseDTO.setTotalPrice(order.getTotalPrice());
        User user = order.getUser();
        orderResponseDTO.setUserId(user.getId());
        orderResponseDTO.setUserName(user.getName());
        orderResponseDTO.setUserSurname(user.getSurname());
        return orderResponseDTO;
    }

    public Order mapOrderRequestDTOToOrder(OrderRequestDTO orderRequestDTO) {
        Order order = new Order();
        order.setId(orderRequestDTO.getId());
        order.setComment(orderRequestDTO.getComment());
        order.setDeliveryAddress(orderRequestDTO.getDeliveryAddress());
        order.setUser(userService.findById(orderRequestDTO.getUserId()).orElseThrow(() ->
                new IllegalStateException("User with the given id wasn't found in database")));
        order.setTotalPrice(orderRequestDTO.getTotalPrice());
        return order;
    }

    private BookResponseForOrderDTO mapOrderProductsToBookResponseForOrderDTO(@Valid OrderProducts products) {
        BookResponseForOrderDTO bookResponseForOrderDTO =
                bookDTOMapper.mapBookToBookResponseForOrderDTO(products.getBook());
        bookResponseForOrderDTO.setNumber(products.getNumber());
        return bookResponseForOrderDTO;
    }
}
